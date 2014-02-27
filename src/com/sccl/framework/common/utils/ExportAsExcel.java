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
 * ȫ��ʹ�ú�̨����Excel�ķ�����
 * </p>
 * 
 * <p>
 * �������ṩҪ����Excel��ģ�壬ģ����˱�ͷ��β��������һ��ʾ�����ݣ�����ᰴ���ĸ�ʽ��������
 * </p>
 * 
 * @param dataArr/method ���ݣ����ݼ� ���� ��Ӧ�ķ�����������֧�����أ�����ֵjson��
 * @param headerArr ���ֶԣ�Ҫ��������������Ӧ�ֶ�����������˳����������ͷ�����ģ������һ����
 * 
 *            <pre>
 * ���� ƾ֤������
 * headerArr = new Array(
 * 	["ժҪ","theRemark"],
 * 	["��ƿ�Ŀ","bursary"],
 * 	["��ϸ��Ŀ","detailCourseId"],
 * 	["�跽����","debitLocalCurrency"],
 * 	["��������","londersLocalCurrency"]);
 * </pre>
 * @param templateFileName
 *            ģ���ļ�����ģ�嶼��/exceltemplatesĿ¼�£�����ֻ���ṩ�ļ������ɣ���������׺�����硰ƾ֤���ɡ���
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
			//ѭ��methods�õ����������Բ�֧������
			for(int i=0; i<theMethods.length; i++) {
				if(theMethods[i].getName().equals(jm.getMethod())) {
					theMethod = theMethods[i];
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return "û���ҵ�����";
		} 
		
		try {
			String str = theMethod.invoke(theObj, jm.getParameters()).toString();
			Type objType = new TypeToken<List<Object>>() {}.getType();
			dataList = new Gson().fromJson(str, objType);
		} catch (Exception e) {
			e.printStackTrace();
			return "���÷���ʧ��";
		} 
		
		
		return export(dataList, headerArray, templateFileName);
	}

	@SuppressWarnings("rawtypes")
	public String export(List dataArr, String[][] headerArray, String templateFileName) {
		
		String reStr = "��Ϣ�������ɹ���";
		List dataList = dataArr;

		if (headerArray == null) {
			reStr = "��Ϣ����ͷ�����ֶԲ���Ϊ��";
		} else {
			try {
				reStr = writeToExcel(dataList, headerArray, templateFileName);
			} catch (Exception e) {
				// �쳣�ر��ļ�
				if (wbIn != null)
					wbIn.close();
				if (wwb != null)
					wbIn.close();
				reStr = "��Ϣ��" + e.getMessage();
				e.printStackTrace();
			}
		}

		return reStr;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String writeToExcel(List dataList, String[][] headerArray, String templateFileName) throws Exception {
		
		/************** ����Ĵ��붼Ϊ�ļ������ļ�·����׼�� ************/
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssms");
		String currDate = sdf.format(new Date());

		String randomStr = "";
		Random random = new Random();
		int length = 4;
		for (int i = 0; i < length; i++) {
			randomStr += String.valueOf(random.nextInt(10));
		}
		String fileName = templateFileName + currDate + randomStr;

		// ģ��·�� /exceltemplates
		String templateFilePath = servletContext.getRealPath("/") + "exceltemplates\\" + templateFileName + ".xls";
		// ���ص�����·����ǰ̨����� http://host/WFM
		String exportFilePath = "/export/" + fileName + ".xls";
		// ������·��
		String exportDir = servletContext.getRealPath("/") + "export";
		// �����ļ��ڷ������ϵľ���·��
		String exportPath = servletContext.getRealPath("/") + "export\\" + fileName + ".xls";

		// ��������Ŀ¼��Excel�ļ�
		File fileOutDir = new File(exportDir);
		if (!fileOutDir.exists()) {
			fileOutDir.mkdirs();
		}
		File fileOut = new File(exportPath);
		if (!fileOut.exists()) {
			fileOut.createNewFile();
		}
		/************** ����Ĵ��붼Ϊ�ļ������ļ�·����׼�� ************/

		// ���������ڸ�ʽ
		SimpleDateFormat outDf = new SimpleDateFormat("yyyy-mm-dd");

		// ��ģ���ļ���һ��Excel��workbook��
		File fileIn = new File(templateFilePath);
		wbIn = Workbook.getWorkbook(fileIn);

		// ��ģ�帴��һ��Excel�ļ��������������
		wwb = Workbook.createWorkbook(fileOut, wbIn);
		wbIn.close(); // ���� �ر��ļ�
		WritableSheet ws = wwb.getSheet(0);

		// ���µ�sheet���ҵ��б��ͷ����
		String firstHeadStr = headerArray[0][0];
		Cell firstHeadCell = ws.findCell(firstHeadStr);
		if (firstHeadCell == null) {
			throw new Exception("��Ϣ��û���ҵ���ͷ");
		}
		// ��ͷ�к�
		int headRowNum = firstHeadCell.getRow();
		// ��ʽ���к� -- ÿ��ģ����һ��ʾ��������Ϊ��ʽ�С�
		int startRowNum = headRowNum + 1;

		// ����������
		int colNum = headerArray.length;

		// ��ʼ�������ݡ�����ÿ��
		Iterator datas = dataList.iterator();
		Map rowMap;// ÿһ��
		List cellList = new ArrayList<Object[]>();
		while (datas.hasNext()) {

			rowMap = new HashMap();
			rowMap = (Map) datas.next();

			// ÿ��һ�У��Ͳ���һ��
			ws.insertRow(++startRowNum);

			for (int i = 0; i < colNum; i++) {
				// ֻ�ڶ���һ������ʱ����ȡʾ���еĸ�ʽ��ȷ����ͷ˳���
				if (startRowNum == headRowNum + 2) {
					// ȡ�ø��еı�ͷ �����ݱ�ͷ��headerArray��ȥ��Ӧ��ȡʲô�ֶε�ֵ
					String tmp_content_str = ws.getCell(i, headRowNum).getContents();
					// �õ���Ԫ���ʽ
					CellFormat cellFormat = ws.getCell(i, headRowNum + 1).getCellFormat();
					// �õ�Ҫ�����ĵ�Ԫ�������ֽ���
					Class cellClazz = ws.getCell(i, headRowNum + 1).getClass();

					int jInt = -1;
					for (int j = 0; j < colNum; j++) {
						if (tmp_content_str.equals(headerArray[j][0].toString())) {
							jInt = j; // ��j��λ���ҵ���
						}
					}
					
					if (jInt == -1) {
						throw new Exception("��Ϣ��ģ��ı�ͷ�Ͳ�������ı�ͷ�Բ���");
					} else {
						Object[] cellObj = { jInt, cellClazz, cellFormat };
						cellList.add(i, cellObj);
					}
				}

				// �ӵڶ����Ժ� jInt cellClazz cellFormat���ֶΣ���ֱ�Ӵ�cellList��ȡ�ˡ�
				Object[] cellObj = (Object[]) cellList.get(i);
				int jInt = (Integer) cellObj[0];
				Class cellClazz = (Class) cellObj[1];
				CellFormat cellFormat = (CellFormat) cellObj[2];

				// headerArray[jInt][1] ����Ҫȡ���ֶ���������psn_job_id
				Object contentObj = rowMap.get(headerArray[jInt][1]);
				WritableCell tmp_cell;
				if (contentObj != null) {
					// �������ݵ����ͺ͵�Ԫ������Ϳ��ܲ�ƥ�䣬����ֱ�Ӹ��ݵ�Ԫ�������������ʵ�������ݡ�
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

					// ��Ԫ��Ĺ��캯��
					Constructor cellConstructor = cellClazz.getConstructor( int.class, int.class, contentClazz, CellFormat.class);

					tmp_cell = (WritableCell) cellConstructor.newInstance(i, startRowNum, contentObj, cellFormat);
				} else {
					tmp_cell = new Blank(i, startRowNum, cellFormat);
				}
				
				ws.addCell(tmp_cell);
			}
		}

		// ɾ����һ�еĸ�ʽʾ���С�
		ws.removeRow(headRowNum + 1);
		// д��Excel������
		wwb.write();
		// �ر�Excel����������
		wwb.close();
		
		// ǰ̨�������������ֵ����path��ͷ���ͱ����������ء�
		return "path:"  + exportFilePath;
	}

	public void setServletContext(ServletContext sc) {
		this.servletContext = sc;
	}

}
