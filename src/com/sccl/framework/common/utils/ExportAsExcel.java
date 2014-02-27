package com.sccl.framework.common.utils;


import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;

import jxl.Cell;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.write.Blank;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sccl.framework.vo.JavaMethod;

/*******
 * <p>
 * 全部使用后台导出Excel的方法。
 * </p>
 * 
 * <p>
 * 服务器提供要导出Excel的模板，模板除了表头表尾，必须留一行示例数据，程序会按它的格式来导出。
 * </p>
 * 
 * @param dataArr/method 数据：数据集 或者 对应的方法，方法不支持重载，返回值json；
 * @param headerArr 列字对：要导出的列名和相应字段名，按导出顺序，列名必须和服务器模板名字一样；
 * 
 *            <pre>
 * 比如 凭证导出：
 * headerArr = new Array(
 * 	["摘要","theRemark"],
 * 	["会计科目","bursary"],
 * 	["明细科目","detailCourseId"],
 * 	["借方本币","debitLocalCurrency"],
 * 	["贷方本币","londersLocalCurrency"]);
 * </pre>
 * @param templateFileName
 *            模板文件名：模板都在/exceltemplates目录下，所以只需提供文件名即可，不包括后缀名，如“凭证生成”。
 * @author wbgen
 *******/
@Component
public class ExportAsExcel implements ServletContextAware {

	private ServletContext servletContext;
	private WritableWorkbook wwb;
	private Workbook wbIn;
	
	@SuppressWarnings("rawtypes")
	public String exportByData (List dataArr, String[][] headerArray, String templateFileName) {
		return export(dataArr, headerArray, templateFileName);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public String exportByMethod (String methodObj, String[][] headerArray, String templateFileName) {
		JavaMethod jm = new Gson().fromJson(methodObj, JavaMethod.class);
		List dataList = new ArrayList();
		ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.servletContext);
		Method[] theMethods = null;
		Method theMethod = null;
		Object theObj = ac.getBean(jm.getBean());
		Class<? extends Object> theClazz = theObj.getClass();
		try {
			theMethods = theClazz.getDeclaredMethods();
			//循环methods得到方法，所以不支持重载
			for(int i=0; i<theMethods.length; i++) {
				if(theMethods[i].getName().equals(jm.getMethod())) {
					theMethod = theMethods[i];
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return "没有找到方法";
		} 
		
		try {
			String str = theMethod.invoke(theObj, jm.getParameters()).toString();
			Type objType = new TypeToken<List<Object>>() {}.getType();
			dataList = new Gson().fromJson(str, objType);
		} catch (Exception e) {
			e.printStackTrace();
			return "调用方法失败";
		} 
		
		
		return export(dataList, headerArray, templateFileName);
	}

	@SuppressWarnings("rawtypes")
	public String export(List dataArr, String[][] headerArray, String templateFileName) {
		
		String reStr = "信息：导出成功。";
		List dataList = dataArr;

		if (headerArray == null) {
			reStr = "信息：表头的列字对不能为空";
		} else {
			try {
				reStr = writeToExcel(dataList, headerArray, templateFileName);
			} catch (Exception e) {
				// 异常关闭文件
				if (wbIn != null)
					wbIn.close();
				if (wwb != null)
					wbIn.close();
				reStr = "信息：" + e.getMessage();
				e.printStackTrace();
			}
		}

		return reStr;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String writeToExcel(List dataList, String[][] headerArray, String templateFileName) throws Exception {
		
		/************** 下面的代码都为文件名、文件路径做准备 ************/
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssms");
		String currDate = sdf.format(new Date());

		String randomStr = "";
		Random random = new Random();
		int length = 4;
		for (int i = 0; i < length; i++) {
			randomStr += String.valueOf(random.nextInt(10));
		}
		String fileName = templateFileName + currDate + randomStr;

		// 模板路径 /exceltemplates
		String templateFilePath = servletContext.getRealPath("/") + "exceltemplates\\" + templateFileName + ".xls";
		// 返回的下载路径，前台会加上 http://host/WFM
		String exportFilePath = "/export/" + fileName + ".xls";
		// 导出的路径
		String exportDir = servletContext.getRealPath("/") + "export";
		// 导出文件在服务器上的绝对路径
		String exportPath = servletContext.getRealPath("/") + "export\\" + fileName + ".xls";

		// 创建导出目录和Excel文件
		File fileOutDir = new File(exportDir);
		if (!fileOutDir.exists()) {
			fileOutDir.mkdirs();
		}
		File fileOut = new File(exportPath);
		if (!fileOut.exists()) {
			fileOut.createNewFile();
		}
		/************** 上面的代码都为文件名、文件路径做准备 ************/

		// 导出的日期格式
		SimpleDateFormat outDf = new SimpleDateFormat("yyyy-mm-dd");

		// 打开模板文件到一个Excel的workbook中
		File fileIn = new File(templateFilePath);
		wbIn = Workbook.getWorkbook(fileIn);

		// 用模板复制一份Excel文件，再往里加数据
		wwb = Workbook.createWorkbook(fileOut, wbIn);
		wbIn.close(); // 读完 关闭文件
		WritableSheet ws = wwb.getSheet(0);

		// 在新的sheet里找到列表表头的行
		String firstHeadStr = headerArray[0][0];
		Cell firstHeadCell = ws.findCell(firstHeadStr);
		if (firstHeadCell == null) {
			throw new Exception("信息：没有找到表头");
		}
		// 表头行号
		int headRowNum = firstHeadCell.getRow();
		// 格式行行号 -- 每个模板留一行示例数据做为格式行。
		int startRowNum = headRowNum + 1;

		// 导出的列数
		int colNum = headerArray.length;

		// 开始插入数据。遍历每行
		Iterator datas = dataList.iterator();
		Map rowMap;// 每一行
		List cellList = new ArrayList<Object[]>();
		while (datas.hasNext()) {

			rowMap = new HashMap();
			rowMap = (Map) datas.next();

			// 每读一行，就插入一行
			ws.insertRow(++startRowNum);

			for (int i = 0; i < colNum; i++) {
				// 只在读第一行数据时，才取示例行的格式、确定表头顺序等
				if (startRowNum == headRowNum + 2) {
					// 取得该列的表头 ，根据表头在headerArray里去找应该取什么字段的值
					String tmp_content_str = ws.getCell(i, headRowNum).getContents();
					// 得到单元格格式
					CellFormat cellFormat = ws.getCell(i, headRowNum + 1).getCellFormat();
					// 得到要导出的单元格的类的字节码
					Class cellClazz = ws.getCell(i, headRowNum + 1).getClass();

					int jInt = -1;
					for (int j = 0; j < colNum; j++) {
						if (tmp_content_str.equals(headerArray[j][0].toString())) {
							jInt = j; // 在j的位置找到了
						}
					}
					
					if (jInt == -1) {
						throw new Exception("信息：模板的表头和参数传入的表头对不上");
					} else {
						Object[] cellObj = { jInt, cellClazz, cellFormat };
						cellList.add(i, cellObj);
					}
				}

				// 从第二行以后 jInt cellClazz cellFormat等字段，就直接从cellList里取了。
				Object[] cellObj = (Object[]) cellList.get(i);
				int jInt = (Integer) cellObj[0];
				Class cellClazz = (Class) cellObj[1];
				CellFormat cellFormat = (CellFormat) cellObj[2];

				// headerArray[jInt][1] 就是要取的字段名，比如psn_job_id
				Object contentObj = rowMap.get(headerArray[jInt][1]);
				WritableCell tmp_cell;
				if (contentObj != null) {
					// 由于内容的类型和单元格的类型可能不匹配，所以直接根据单元格的类型来重新实例化内容。
					Class contentClazz;
					if (cellClazz == jxl.write.Boolean.class) {
						contentClazz = boolean.class;
						contentObj = new Boolean(contentObj.toString());
					} else if (cellClazz == jxl.write.Number.class) {
						contentClazz = double.class;
						contentObj = new Double(contentObj.toString());
					} else if (cellClazz == jxl.write.DateTime.class) {
						contentClazz = Date.class;
						contentObj = outDf.parse(contentObj.toString());
					} else {
						contentClazz = String.class;
						contentObj = contentObj.toString();
					}

					// 单元格的构造函数
					Constructor cellConstructor = cellClazz.getConstructor( int.class, int.class, contentClazz, CellFormat.class);

					tmp_cell = (WritableCell) cellConstructor.newInstance(i, startRowNum, contentObj, cellFormat);
				} else {
					tmp_cell = new Blank(i, startRowNum, cellFormat);
				}
				
				ws.addCell(tmp_cell);
			}
		}

		// 删除第一行的格式示例行。
		ws.removeRow(headRowNum + 1);
		// 写入Excel工作表
		wwb.write();
		// 关闭Excel工作薄对象
		wwb.close();
		
		// 前台分析，如果返回值不是path开头，就报错，否则下载。
		return "path:"  + exportFilePath;
	}

	public void setServletContext(ServletContext sc) {
		this.servletContext = sc;
	}

}
