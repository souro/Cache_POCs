/**
 * 
 */
package com.souro.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import com.souro.dao.ResearchDao;

/**
 * @author sourabrata
 *
 */
public class ResearchService{
	private static final Logger logg = LoggerFactory.getLogger(ResearchService.class);
	
	@Cacheable(value="Souro_MSCache1")
	public ResearchDao getDetails(String id){
		ResearchDao research = new ResearchDao();
		research.setResearch_id(id);
		research.setResearch_duration(3.5);
		research.setResearch_field("Machine Learning");
		System.out.println("Value (for Id: "+ id  +") is not getting accessed from Cache");
		//logg.info("Value (for Id: "+ id  +") is not getting retrieved from Cache");
		return research;
	}
	
/*	@CacheEvict(value="research")
	public void saveDetails(Research research){
		
	}
	
	@CachePut
	public void create(int id){
		
	}*/

}
