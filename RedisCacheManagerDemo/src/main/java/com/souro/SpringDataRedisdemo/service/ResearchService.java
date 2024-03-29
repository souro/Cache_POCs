/**
 * 
 */
package com.souro.SpringDataRedisdemo.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.souro.SpringDataRedisdemo.dao.Research;

/**
 * @author sourabrata
 *
 */

@Service
public class ResearchService{
	//@Cacheable(value="research", condition="#id==3")
	@Cacheable(value="research")
	public Research getDetails(int id){
		Research research = new Research();
		research.setResearch_id(id);
		research.setResearch_duration(3.5);
		research.setResearch_field("Machine Learning");
		System.out.println("Value (for Id: "+ id  +") is not getting accessed from Cache");
		return research;
	}
	
	
/*	@CacheEvict(value="research")
	public void saveDetails(Research research){
		
	}
	
	@CachePut
	public void create(int id){
		
	}*/
}
