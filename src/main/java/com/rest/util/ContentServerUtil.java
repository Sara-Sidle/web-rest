package com.rest.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.rest.util.tibco.MySendMessage;
import com.rest.util.tibco.MySendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rest.util.tibco.MySendMessage;
import com.ryx.framework.utils.DateUtil;
import com.ryx.framework.utils.HttpUtil;
import com.ryx.framework.utils.IDUtil;
import com.ryx.framework.utils.JsonUtil;

/**
 * 内容服务平台工具类
 *
 * @author
 */
public class ContentServerUtil {

    private static final String HOST = "http://10.3.30.48";

    private static final Logger LOG = LoggerFactory.getLogger(ContentServerUtil.class);

    private static final String VERIFY_URL = HOST + ":30100/cmbc/0030";

    private static final String ACCOUNT_URL = HOST + ":30100/cmbc/0010";

    private static String randString = "0123456789";// 随机产生的字符串
    private static int length = 4;
    private static String branchId = "agent";

    /**
     * @param phone 手机号
     * @param
     * @return
     */
    public static Map<String, String> sendMessage(String phone) {
        String code = getRandomStr();
        String msg = DateUtil.getToday() + "|" + DateUtil.getTime() + "|111111|" + phone
                + "|感谢使用,您的验证码:" + code + ",请在10分钟内使用,勿泄露!非本人交易请致电客服。|" + branchId;
        MySendMessage.getInstance().toSend(msg);
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("send_code", code);
        LOG.info("send sms：result" + resultMap.toString());
        return resultMap;
    }

    public static void main(String[] args) {
        System.out.println(sendMessage("18668917331"));
//		String a = "感谢使用,您的验证码:5277,请在10分钟内使用,勿泄露!非本人交易请致电客服。";
//		System.out.println(a.indexOf(":"));
//		System.out.println(a.substring(a.indexOf(":") + 1, a.indexOf(":") + 5));
    }


    /**
     * 获得随机验证码
     *
     * @return
     */
    private static String getRandomStr() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < length; i++) {
            String str = String.valueOf(randString.charAt(random.nextInt(randString.length())));
            s.append(str);
        }
        return s.toString();
    }

    /**
     * @param userName 姓名
     * @param pId      身份证号
     * @return
     */
    public static Map<String, String> verify(String userName, String pId) {
        Map<String, String> paramMap = new HashMap<String, String>();
        String serial = IDUtil.getRandomId32();
        paramMap.put("Transcode", "000020");
        paramMap.put("SytermId", "100001");
        paramMap.put("SerialNo", serial);
        paramMap.put("TransDate", DateUtil.getToday());
        paramMap.put("TransTime", DateUtil.getTime());
        paramMap.put("TrueName", userName);
        paramMap.put("CertType", "ZR01");
        paramMap.put("CertNo", pId);
        paramMap.put("ReturnPic", "1");
        LOG.info("verify：param" + paramMap.toString());
        String resultStr = HttpUtil.post(VERIFY_URL, JsonUtil.map2json(paramMap));
        Map<String, String> resultMap = JsonUtil.json2Map(resultStr);
        resultMap.put("serial_no", serial);
        LOG.info("verify:result：" + resultMap.toString());
        return resultMap;
    }

    /**
     * @param bankNo      银行卡号
     * @param accountName 户名
     * @param pId         身份证号
     * @param 姓名
     * @return
     */
    public static Map<String, String> accountVerify(String bankNo, String accountName, String pId) {
        Map<String, String> paramMap = new HashMap<String, String>();
        String serial = IDUtil.getRandomId32();
        paramMap.put("Transcode", "000010");
        paramMap.put("Serial", serial);
        paramMap.put("SytermId", "100001");
        paramMap.put("BankNo", bankNo);
        paramMap.put("UserName", accountName);
        paramMap.put("IdentityType", "ZR01");
        paramMap.put("IdentityId", pId);
        paramMap.put("StartDate", DateUtil.getToday());
        paramMap.put("StartTime", DateUtil.getTime());
        LOG.info("account-verify：param" + paramMap.toString());
        String resultStr = HttpUtil.post(ACCOUNT_URL, JsonUtil.map2json(paramMap));
        Map<String, String> resultMap = JsonUtil.json2Map(resultStr);
        resultMap.put("serial_no", serial);
        LOG.info("account-verify：result：" + resultMap.toString());
        return resultMap;
    }

    // {CertPicture=http://119.254.93.91:8896/master/upload/secret/2017/07/27/e6030895acdf1b216ec8e3979a23712b.jpg,
    // ExecCode=00, ExecMsg=交易成功, ExecType=S, PaySerialNo=2017072708415623,
    // ReqSerialNo=0170727027805225, Resv=, TransDate=20170727,
    // TransTime=231104, ValidateStatus=00, channel=XMMS,
    // platSerial=0170727027805225}
//	public static void main(String[] args) {
//		// System.out.println(ContentServerUtil.verify("武庆超",
//		// "370829199211074917"));
//		// 后台账户：60007045
//		// 姓名：万存宝
//		// 身份证号：640321199307010536
//		// 建行卡：6217004470017491452
//		// 手机号：15809653331
//		System.out.println(accountVerify("6217004470017491452", "万存宝", "640321199307010536"));
//	}
}
