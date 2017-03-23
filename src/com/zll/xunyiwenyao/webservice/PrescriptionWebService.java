package com.zll.xunyiwenyao.webservice;

import java.util.List;

import com.zll.xunyiwenyao.dbitem.Prescription;

public class PrescriptionWebService {
	
	private static List<Prescription> prescriptionlist;

	public static void AddPrescription(Prescription item){
		prescriptionlist.add(item);
	}
}
