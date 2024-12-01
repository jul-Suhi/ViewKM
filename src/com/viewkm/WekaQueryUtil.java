package com.viewkm;

import java.util.ArrayList;
import java.util.List;

import weka.core.Attribute;
import weka.core.Instances;
import weka.experiment.InstanceQuery;

public class WekaQueryUtil {
	/**
	 * 连接数据�? 
	 * @param tableName
	 * @param filterAttributeList
	 * @return
	 * @throws Exception
	 */
	public static Instances executeQuery(String tableName,
			List filterAttributeList) throws Exception {
		InstanceQuery iq = new InstanceQuery();
		iq.setUsername("root");
		iq.setPassword("123");
		iq.setQuery("select * from " + tableName);
		Instances instances = iq.retrieveInstances();
		iq.close();
		return instances;
	}

	/**
	 * 获取当前数据集的属�??
	 * 
	 * @param instances
	 * @return
	 */
	public static ArrayList getAttributes(Instances instances) {
		ArrayList m_Attributes = new ArrayList();
		for (int i = 0; i < instances.numAttributes(); i++) {
			m_Attributes.add(instances.attribute(i).name());
		}
		return m_Attributes;
	}

	public static List<ArrayList> convertInstances(Instances instances) {
		List list = new ArrayList();
		for (int i = 0; i < instances.numInstances(); i++) {
			ArrayList instanceList = new ArrayList();
			for (int j = 0; j < instances.numAttributes(); j++) {
				if (instances.attribute(j).type() == Attribute.NUMERIC) {// 数字
					instanceList.add(instances.instance(i).value(j));
				}
				if (instances.attribute(j).type() == Attribute.NOMINAL) {// 枚举
					instanceList.add(instances.instance(i).stringValue(j));
				} else if (instances.attribute(j).type() == Attribute.STRING) {// 字符
					instanceList.add(instances.instance(i).stringValue(j));
				}
			}
			list.add(instanceList);
		}
		return list;
	}
}
