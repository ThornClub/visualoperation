package cc.sgee.visualoperation.common.utils;

import org.springframework.stereotype.Component;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Zhs
 * @Date: 2020/12/3 22:49
 * @Description: 获取系统信息
 */
@Component
public class GetSystemInfoUtils {

    private static final oshi.SystemInfo os = new oshi.SystemInfo();

    /**
     * 获取负载
     * @return double
     */
    public double getLoad(){
        return ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();
    }

    /**
     * 获取系统核心
     * @return int
     */
    public int getCpuCore(){
        return os.getHardware().getProcessor().getLogicalProcessorCount();
    }

    /**
     * 获取cpu使用率
     * @return
     * @throws InterruptedException
     */
    public String getCpuRate() throws InterruptedException {
        CentralProcessor processor = os.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 睡眠1s
        Thread.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        return new DecimalFormat("#.##%").format(1.0-(idle * 1.0 / totalCpu));
    }

    /**
     * 获取内存信息
     */
    public Map<String,String> getMemory(){
        Map<String,String> memory_info = new HashMap<>(4);
        GlobalMemory memory = os.getHardware().getMemory();
        //总内存
        long totalByte = memory.getTotal();
        //剩余
        long acaliableByte = memory.getAvailable();
        memory_info.put("all",formatByte(totalByte));
        memory_info.put("use",formatByte(totalByte-acaliableByte));
        memory_info.put("free",formatByte(acaliableByte));
        memory_info.put("rate",new DecimalFormat("#.##%").format((totalByte-acaliableByte)*1.0/totalByte));
        return memory_info;
    }

    public Map<String,String> getDisk(){
        Map<String,String> disk_info = new HashMap<>(3);
        File file = new File("/");
        long rom = file.getTotalSpace();
        long usedrom = rom - file.getUsableSpace();
        double romusage = ((double) usedrom / (double) rom) * 100;
        disk_info.put("rom",formatByte(rom));
        disk_info.put("usedrom",formatByte(usedrom));
        disk_info.put("romusage",new DecimalFormat("#.00").format(romusage));
        return disk_info;
    }

    public static String formatByte(long byteNumber){
        //换算单位
        double FORMAT = 1024.0;
        double kbNumber = byteNumber/FORMAT;
        if(kbNumber<FORMAT){
            return new DecimalFormat("#KB").format(kbNumber);
        }
        double mbNumber = kbNumber/FORMAT;
        if(mbNumber<FORMAT){
            return new DecimalFormat("#MB").format(mbNumber);
        }
        double gbNumber = mbNumber/FORMAT;
        if(gbNumber<FORMAT){
            return new DecimalFormat("#GB").format(gbNumber);
        }
        double tbNumber = gbNumber/FORMAT;
        return new DecimalFormat("#TB").format(tbNumber);
    }
}
