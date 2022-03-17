package com.psbc.rpa.util;

/**
 * @author dahua
 * @time 2022/3/9 9:51
 */
public class JsUtil {

    public static String buildJs(StringBuilder... jss) {
        StringBuilder result = new StringBuilder();
        for (StringBuilder js : jss) {
            result.append(js);
        }
        return new String(result);
    }

    public static StringBuilder head(int timeout, Boolean headless, String downloadsPath, String executablePath, String storageState) {
        if (headless == null) {
            headless = true;
        }
        if (downloadsPath == null) {
            downloadsPath = "os.homedir() + '/Downloads'";
        }
        if (executablePath == null) {
            executablePath = "C:/Program Files/Google/Chrome/Application/chrome.exe";
        }
        StringBuilder js = new StringBuilder("(async () => {" +
                "const browser = await chromium.launch({" +
                "timeout: " + timeout + "," +
                "headless: " + headless + "," +
//                "downloadsPath: " + downloadsPath + "," +
                "executablePath: '" + executablePath + "'" +
                "});");
        if (storageState == null) {
            js.append("const context = await browser.newContext();");
        } else {
            js.append("const context = await browser.newContext({storageState: '" + storageState + "'});");
        }
        js.append("const page = await context.newPage();");
        return js;
    }

    public static StringBuilder foot() {
        return new StringBuilder("\nawait page.close();").append("})();");
    }

    public static StringBuilder detail() {
        return new StringBuilder("await page.goto('https://note.youdao.com/web/');\n" +
                "\n" +
                "        await page.locator('.ad-screen-content .ad-close svg use').click();\n" +
                "\n" +
                "        await page.locator('[placeholder=\"搜索全部笔记\"]').click();\n" +
                "        await Promise.all([\n" +
                "            page.locator('[placeholder=\"搜索全部笔记\"]').fill('rpa测试')\n" +
                "        ]);\n" +
                "        await page.locator('span:has-text(\"rpa测试\")').click();\n" +
                "        await page.frameLocator('#bulb-editor').locator('text=1.xlsx').click({\n" +
                "            button: 'right'\n" +
                "        });\n" +
                "        const [download] = await Promise.all([\n" +
                "            page.waitForEvent('download'),\n" +
                "            page.locator('text=附件另存为').click()\n" +
                "        ]);\n" +
                "        const downloadPath = await download.path()\n" +
                "        console.log(downloadPath)\n" +
                "        await download.saveAs(desktopPath + '3.xlsx')");
    }

    public static StringBuilder excel() {
        return new StringBuilder(
                "\nlet workbook = xlsx.parse(desktopPath + \"3.xlsx\");" +
                        "const sheet = workbook[0].data;" +
                        "const data = [];" +
                        "for (let i = 0; i < sheet.length; i++) {" +
                        "const col1 = sheet[i][0];" +
                        "const col2 = sheet[i][1];" +
                        "const col3 = col1 + col2;" +
                        "data.push([col1, col2, col3]);" +
                        "}" +
                        "let buffer = xlsx.build([" +
                        "{" +
                        "name: '测试数据'," +
                        "data: data" +
                        "}" +
                        "]);" +
                        "fs.writeFileSync(desktopPath + '2.xlsx', buffer, {'flag': 'w'});");
    }
}
