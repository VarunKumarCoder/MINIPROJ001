  package com.nt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.config.AppConfigProperties;
import com.nt.constants.TravelPlanConstants;
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
		System.out.println("Messages:: "+messages);
	}

	@Override
	public String saveTravelPlan(TravelPlan plan) {
		TravelPlan saved = travelPlanRepo.save(plan);
		/*if (saved.getPlanid() != null)
			return "TravelPlan is saved with the Id value of ::" + saved.getPlanid();
		else
			return "Problem in saving the TravelPlan";*/
		//return saved.getPlanid()!=null?"TravelPlan is saved with The ID No ::"+saved.getPlanid():"Problem in saving TourPlan";
		return saved.getPlanid()!=null?messages.get(TravelPlanConstants.SAVE_SUCCESS)+saved.getPlanid():messages.get(TravelPlanConstants.SAVE_FAILURE);
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
		return travelPlanRepo.findById(planId).orElseThrow(()->new IllegalArgumentException(messages.get(TravelPlanConstants.FIND_BY_ID_FAILURE)));
	}

	@Override
	public String updateTravelPlan(TravelPlan plan) {
		/*TravelPlan updated=travelPlanRepo.save(plan);
		return updated.getPlanid()+"TravelPlan is Updated";*/
		Optional<TravelPlan> opt=travelPlanRepo.findById(plan.getPlanid());
		if(opt.isPresent()) {
			 travelPlanRepo.save(plan);
			 return plan.getPlanid()+messages.get(TravelPlanConstants.UPDATE_SUCCESS);
		}
		else {
			return plan.getPlanid()+messages.get(TravelPlanConstants.UPDATE_FAILURE);
		}
	}

	@Override
	public String deleteTravelPlan(Integer planId) {
		Optional<TravelPlan> opt=travelPlanRepo.findById(planId);
		if(opt.isPresent()) {
			travelPlanRepo.deleteById(planId);
			return planId+messages.get(TravelPlanConstants.DELETE_SUCCESSE);
		}
		else {
			return planId+messages.get(TravelPlanConstants.DELETE_FAILURE);
		}
	}

	@Override
	public String changeTravelPlanStatus(Integer planId, String status) {
		Optional<TravelPlan> opt=travelPlanRepo.findById(planId);
		if(opt.isPresent()) {
			TravelPlan plan=opt.get();
			plan.setActivateSW(status);
			travelPlanRepo.save(plan);
			return planId+messages.get(TravelPlanConstants.STATUS_CHANGE_SUCCESS);
		}
		else {
			return planId+messages.get(TravelPlanConstants.STATUS_CHANGE_FAILURE);
		}
	}

}
