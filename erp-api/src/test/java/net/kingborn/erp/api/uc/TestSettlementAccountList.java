package net.kingborn.erp.api.uc;

import net.kingborn.erp.api.TestUtil;
import net.kingborn.test.BaseUrl;
import net.kingborn.test.TestCommand;
import org.junit.Test;

/**
 * 测试结算账户列表
 */
public class TestSettlementAccountList extends TestCommand {

    @Override
    public void init() throws Exception {
        headerMap = TestUtil.getAuthHeader();
    }

    @Test
    @Override
    public void doTest() {
        System.out.println(formatJson(doPost()));
    }

    @Override
    protected BaseUrl getBaseUrl() {
        return new BaseUrl("localhost", 9090, "/settlementAccount/list");
    }
}
/*
{
	"code":0,
	"data":{
		"accountList":[
			{
				"updatedTime":"2020-08-17 14:41:30",
				"code":"ICBC01",
				"currentBalance":100000,
				"typeName":"银行存款",
				"name":"工商银行1号111",
				"createdTime":"2020-08-17 14:41:30",
				"id":"1295128579910197250",
				"balanceTime":"2020-08-17 06:41:27",
				"type":"1294765881575636993",
				"beginBalance":100000
			},
			{
				"updatedTime":"2020-08-21 22:18:16",
				"code":"ICBC02",
				"currentBalance":10000000,
				"typeName":"银行存款",
				"name":"工商银行2号",
				"createdTime":"2020-08-21 22:18:16",
				"id":"1296693080669302785",
				"balanceTime":"2020-08-21 14:18:12",
				"type":"1294765881575636993",
				"beginBalance":10000000
			},
			{
				"updatedTime":"2020-08-21 22:21:27",
				"code":"ICBC02",
				"currentBalance":10000000,
				"typeName":"银行存款",
				"name":"工商银行2号11",
				"createdTime":"2020-08-21 22:21:27",
				"id":"1296693882423103490",
				"balanceTime":"2020-08-21 14:21:24",
				"type":"1294765881575636993",
				"beginBalance":10000000
			},
			{
				"updatedTime":"2020-08-21 22:23:31",
				"code":"ICBC03",
				"currentBalance":111111,
				"typeName":"银行存款",
				"name":"工商银行3号",
				"createdTime":"2020-08-21 22:23:31",
				"id":"1296694402999144450",
				"balanceTime":"2020-08-21 00:00:00",
				"type":"1294765881575636993",
				"beginBalance":111111
			},
			{
				"updatedTime":"2020-08-21 22:51:05",
				"code":"ces1",
				"currentBalance":11111,
				"typeName":"银行存款",
				"name":"测试111",
				"createdTime":"2020-08-21 22:51:05",
				"id":"1296701339455201282",
				"balanceTime":"2020-08-21 00:00:00",
				"type":"1294765881575636993",
				"beginBalance":11111
			}
		]
	},
	"success":true,
	"message":"success"
}
 */
