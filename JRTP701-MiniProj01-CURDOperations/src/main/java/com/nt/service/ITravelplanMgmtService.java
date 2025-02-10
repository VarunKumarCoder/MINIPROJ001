package com.nt.service;

import java.util.List;
import java.util.Map;

import com.nt.entity.TravelPlan;

public interface ITravelplanMgmtService {
		public String saveTravelPlan(TravelPlan plan);
		public Map<Integer,String> getTravelPlanCategories();
		public List<TravelPlan> showAllTravelPlans();
		public TravelPlan showTravelPlanById(Integer planId);
		public String updateTravelPlan(TravelPlan plan);
		public String deleteTravelPlan(Integer planId);
		public String changeTravelPlanStatus(Integer planId,String status);
}
