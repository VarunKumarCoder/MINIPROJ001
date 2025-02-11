  package com.nt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.config.AppConfigProperties;
import com.nt.entity.PlanCategory;
import com.nt.entity.TravelPlan;
import com.nt.repository.ITravelPlanCategoryRepository;
import com.nt.repository.ITravelPlanRepository;

@Service
public class TravelPlanMgmtServiceImpl implements ITravelplanMgmtService {
	@Autowired
	private ITravelPlanCategoryRepository planCategoryRepo;
	@Autowired
	private ITravelPlanRepository travelPlanRepo;
	
	private Map<String, String> messages;
	
	@Autowired
	public TravelPlanMgmtServiceImpl(AppConfigProperties props) {
		messages=props.getMessages();
	}

	@Override
	public String saveTravelPlan(TravelPlan plan) {
		TravelPlan saved = travelPlanRepo.save(plan);
		/*if (saved.getPlanid() != null)
			return "TravelPlan is saved with the Id value of ::" + saved.getPlanid();
		else
			return "Problem in saving the TravelPlan";*/
		return saved.getPlanid()!=null?"TravelPlan is saved with The ID No ::"+saved.getPlanid():"Problem in saving TourPlan";
	}

	@Override
	public Map<Integer, String> getTravelPlanCategories() {
		List<PlanCategory> list=planCategoryRepo.findAll();
		Map<Integer, String> categoriesMap=new HashMap<Integer, String>();
		list.forEach(category->{
			categoriesMap.put(category.getCategoryd(), category.getCategoryName());
		});
		return categoriesMap;
	}

	@Override
	public List<TravelPlan> showAllTravelPlans() {
		
		return travelPlanRepo.findAll();
	}

	@Override
	public TravelPlan showTravelPlanById(Integer planId) {
		/*Optional<TravelPlan> opt=travelPlanRepo.findById(planId);
		if(opt.isPresent()) {
			return opt.get();
		}
		else {
			throw new IllegalArgumentException("Plan ID not found");
		}*/
		return travelPlanRepo.findById(planId).orElseThrow(()->new IllegalArgumentException("TravelPlan Not Found"));
	}

	@Override
	public String updateTravelPlan(TravelPlan plan) {
		/*TravelPlan updated=travelPlanRepo.save(plan);
		return updated.getPlanid()+"TravelPlan is Updated";*/
		Optional<TravelPlan> opt=travelPlanRepo.findById(plan.getPlanid());
		if(opt.isPresent()) {
			 travelPlanRepo.save(plan);
			 return plan.getPlanid()+" Is Updated";
		}
		else {
			return plan.getPlanid()+" TravelPlan is not Found";
		}
	}

	@Override
	public String deleteTravelPlan(Integer planId) {
		Optional<TravelPlan> opt=travelPlanRepo.findById(planId);
		if(opt.isPresent()) {
			travelPlanRepo.deleteById(planId);
			return planId+ " TravelPlan is Deleted";
		}
		else {
			return planId+" Travel Plan is not found";
		}
	}

	@Override
	public String changeTravelPlanStatus(Integer planId, String status) {
		Optional<TravelPlan> opt=travelPlanRepo.findById(planId);
		if(opt.isPresent()) {
			TravelPlan plan=opt.get();
			plan.setActivateSW(status);
			travelPlanRepo.save(plan);
			return planId+" TravelPlan is updated";
		}
		else {
			return planId+" Travel Plan is not found";
		}
	}

}
