import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProductLog {
    private String startTime = "2018-01-01";
    private String endTime = "2018-12-31";

    //生产数据
    //存放电话号码
    private List<String> phoneList = new ArrayList<>();
    //存放电话(号码,人)
    private Map<String, String> phoneNameMap = new HashMap();

    public void initPhone() {
        //20个电话号码
        phoneList.add("17078388295");
        phoneList.add("13980337439");
        phoneList.add("14575535933");
        phoneList.add("19902496992");
        phoneList.add("18549641558");
        phoneList.add("17005930322");
        phoneList.add("18468618874");
        phoneList.add("18576581848");
        phoneList.add("15978226424");
        phoneList.add("15542823911");
        phoneList.add("17526304161");
        phoneList.add("15422018558");
        phoneList.add("17269452013");
        phoneList.add("17764278604");
        phoneList.add("15711910344");
        phoneList.add("15714728273");
        phoneList.add("16061028454");
        phoneList.add("16264433631");
        phoneList.add("17601615878");
        phoneList.add("15897468949");

        //20个号码 + 人
        phoneNameMap.put("17078388295", "李雁");
        phoneNameMap.put("13980337439", "卫艺");
        phoneNameMap.put("14575535933", "仰莉");
        phoneNameMap.put("19902496992", "陶欣悦");
        phoneNameMap.put("18549641558", "施梅梅");
        phoneNameMap.put("17005930322", "金虹霖");
        phoneNameMap.put("18468618874", "魏明艳");
        phoneNameMap.put("18576581848", "华贞");
        phoneNameMap.put("15978226424", "华啟倩");
        phoneNameMap.put("15542823911", "仲采绿");
        phoneNameMap.put("17526304161", "卫丹");
        phoneNameMap.put("15422018558", "戚丽红");
        phoneNameMap.put("17269452013", "何翠柔");
        phoneNameMap.put("17764278604", "钱溶艳");
        phoneNameMap.put("15711910344", "钱琳");
        phoneNameMap.put("15714728273", "缪静欣");
        phoneNameMap.put("16061028454", "焦秋菊");
        phoneNameMap.put("16264433631", "吕访琴");
        phoneNameMap.put("17601615878", "沈丹");
        phoneNameMap.put("15897468949", "褚美丽");


    }


    public String product() {
        //主叫
        String caller = null;
        //被叫
        String callee = null;
        //主叫姓名
        String callerName = null;
        //被叫姓名
        String calleeName = null;
        //随机索引

        //取得主叫号码
        int callerIndex = (int) (Math.random() * phoneList.size());
        caller = phoneList.get(callerIndex);
        callerName = phoneNameMap.get(caller);

        while (true) {
            //取得被叫号码
            int calleeIndex = (int) (Math.random() * phoneList.size());
            callee = phoneList.get(calleeIndex);
            calleeName = phoneNameMap.get(callee);
            if (!caller.equals(callee)) break;
        }

        //随机建立通话时间
        String buildTime = randomBuildTime(startTime, endTime);
        //规定通话时长的格式化限制
        DecimalFormat df = new DecimalFormat("0000");
        String duration = df.format((int) (30 * 60 * Math.random()));
        StringBuffer sb = new StringBuffer();
        sb.append(caller + ",").append(callee + ",").append(buildTime + ",").append(duration);
        return sb.toString();
    }


    public String randomBuildTime(String startTime, String endTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            if (endDate.getTime() <= startDate.getTime()) return null;

            long randomTS = startDate.getTime() + (long) ((endDate.getTime() - startDate.getTime()) * Math.random());

            Date resultDate = new Date(randomTS);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String resultTimeString = sdf2.format(resultDate);
            return resultTimeString;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    public void writeLog(String filePath) {
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(filePath, true), "UTF-8");

            while (true) {
                Thread.sleep(500);
                String log = product();
                writer.write(log + "\n");
                writer.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void main(String[] args) {
        if (args == null || args.length <= 0) return;

        ProductLog productLog = new ProductLog();
        productLog.initPhone();
        productLog.product();
        productLog.writeLog(args[0]);

    }
}
