package com.viewkm;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DistanceFunction;
import weka.core.Instances;
import weka.core.ManhattanDistance;
import weka.core.converters.ConverterUtils.DataSource;

import com.viewkm.DataSourceEntity;
import com.viewkm.ImportArrfDataService;
import com.viewkm.WekaQueryUtil;

import net.sf.json.JSONObject;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.LineType;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Scatter;
import com.github.abel533.echarts.series.Series;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class KmeansAction extends ActionSupport {

	private ImportArrfDataService importArrfDataService;
	private int id;
	private ArrayList attributorList = new ArrayList();// 训练后结果的属性值
	private List<ArrayList> instancesList = new ArrayList<ArrayList>(); // 训练后结果的
																		// 数据值
	private int clusterNum;// 簇的个数
	private String jOption;
	static String num;

	public String getjOption() {
		return jOption;
	}

	public String kmeansDisplay() throws Exception {

		return SUCCESS;
	}

	// 拼凑echarts的option并转换成json
	public String getData() throws Exception {

		Option myOption = new GsonOption();
		myOption.title().text("聚类结果");
		List<String> clustersName = new ArrayList<String>();
		for (int i = 0; i < clusterNum; i++) {
			clustersName.add("簇" + i);
		}

		myOption.legend(clustersName.toArray());
		myOption.toolbox().show(true).feature(com.github.abel533.echarts.code.Tool.dataView,
				com.github.abel533.echarts.code.Tool.mark, com.github.abel533.echarts.code.Tool.saveAsImage);
		myOption.tooltip().axisPointer().type(PointerType.cross).lineStyle().type(LineType.dashed).width(1);
		myOption.tooltip().show(true).formatter("{a} <br/> {c} ");
		ValueAxis valueAxisX = new ValueAxis();
		valueAxisX.axisLabel().formatter("{value} 单位");
		myOption.xAxis(valueAxisX);
		ValueAxis valueAxisY = new ValueAxis();
		valueAxisY.axisLabel().formatter("{value} 单位");
		myOption.yAxis(valueAxisY);

		List<Series> allSeries = new ArrayList<Series>();
		Scatter cluScatter;

		for (int i = 0; i < clusterNum; i++) {
			cluScatter = new Scatter();
			cluScatter.name(clustersName.get(i));

			for (int j = 0; j < instancesList.size(); j++) {
				String cluString = instancesList.get(j).get(attributorList.size() - 1).toString();
				double dTemp = Double.parseDouble(cluString);
				if ((int) dTemp == i) {
					cluScatter.data(instancesList.get(j));
				}
			}

			allSeries.add(cluScatter);
		}

		// 装载Series
		myOption.series(allSeries);

		// 封装json
		String jOption = myOption.toString();
		System.out.print(jOption);
		return jOption; // 返回jOption
	}

	public String kmeansIndex() throws Exception {
		return SUCCESS;
	}

	public String kmeansCluster() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		jOption = new String();
		String path = "D:" + "\\" + "upload";// 路径
		String name = request.getParameter("filename");// 文件名
		DataSource datasource = new DataSource(path + "\\" + name);
		Instances instances = datasource.getDataSet();
		List instancesList2 = WekaQueryUtil.convertInstances(instances);

		instances.insertAttributeAt(new Attribute("clusterRecord", Attribute.STRING), instances.numAttributes());
		SimpleKMeans kmeans = new SimpleKMeans();
		Instances tempIns = null;

		HttpServletRequest httpServletRequest = (HttpServletRequest) ActionContext.getContext()
				.get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
		if (httpServletRequest.getParameter("num") != null)
			num = httpServletRequest.getParameter("num");
		int numOfClusters = Integer.valueOf(num);

		// 设置聚类要得到的类别数量
		kmeans.setNumClusters(numOfClusters);
		kmeans.setDistanceFunction(new ManhattanDistance());
		kmeans.buildClusterer(instances);
		// 获取中心点
		tempIns = kmeans.getClusterCentroids();
		DistanceFunction distFn = kmeans.getDistanceFunction();
		clusterNum = kmeans.getNumClusters();
		// 重新计算距离
		reSetDistance(tempIns, instances, distFn);
		// 记录结果，分离属性和对应值
		attributorList.addAll(WekaQueryUtil.getAttributes(instances));
		instancesList.addAll(WekaQueryUtil.convertInstances(instances));

		request.setAttribute("attributorList", attributorList);
		request.setAttribute("instancesList", instancesList);
		request.setAttribute("pId", id);
		request.setAttribute("name", name);
		jOption = getData();

		HttpServletRequest servletRequest = ServletActionContext.getRequest();
		HttpSession session = servletRequest.getSession();

		return SUCCESS;
	}

	// 将数据分配到每个簇
	private void reSetDistance(Instances tempIns, Instances instances, DistanceFunction distFn) {
		for (int i = 0; i < instances.numInstances(); i++) {
			List<Double> tempList = new ArrayList<Double>();
			for (int j = 0; j < tempIns.numInstances(); j++) {
				tempList.add(new Double(distFn.distance(instances.instance(i), tempIns.instance(j))));
			}
			int indexCluster;
			indexCluster = getIndexCluster(tempList);
			instances.instance(i).setValue(instances.numAttributes() - 1, indexCluster);
		}
	}

	// 比较距离，找出属于哪个簇
	private int getIndexCluster(List<Double> tempList) {
		int i, flag;
		flag = 0;
		for (i = flag; i < tempList.size();) {
			// 距离最小，就属于哪个簇
			if (tempList.get(i) < tempList.get(flag)) {
				flag = i;
				i++;
			} else
				i++;
		}
		return flag;
	}

	public ImportArrfDataService getImportArrfDataService() {
		return importArrfDataService;
	}

	public void setImportArrfDataService(ImportArrfDataService importArrfDataService) {
		this.importArrfDataService = importArrfDataService;
	}
}
