package com.health.search1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.health.model.Tutorial;
import com.health.repository.TutorialRepository;

@Component
public class SearchByWord {
	
	@Autowired
	private TutorialRepository tutorialRepo;
	
	public Page<Tutorial>  SearchOutlineByWords(String query,Pageable page){
		
		Page<Tutorial> tutorialPageList=null;
		
		List<String> qList = new ArrayList<String>(Arrays.asList(query.split(" ")));
		int queryLength=qList.size();
		if(queryLength>5) {
			queryLength=5;
		}
		
		tutorialPageList= tutorialRepo.findByOutlineByQueryPagination(query, page);
		
		if(!tutorialPageList.isEmpty()) {
			return tutorialPageList;
		}
		
		else {
			
			if(queryLength<=5) {
				if(queryLength==5) {
					
					tutorialPageList=tutorialRepo.findByOutlineByQuerywords5(qList.get(0), qList.get(1), qList.get(2),qList.get(3),qList.get(4), page);
					if(tutorialPageList.isEmpty()) {
						for(int i=0; i<5; i++) {
							
							if(i==3 || i==4) {
								tutorialPageList=tutorialRepo.findByOutlineByQuerywords4(qList.get(0), qList.get(1), qList.get(2),qList.get(i), page);
								if(!tutorialPageList.isEmpty()) {
									break;
									
								}
								
							}
							if(i==4) {
								tutorialPageList=tutorialRepo.findByOutlineByQuerywords4(qList.get(0), qList.get(2), qList.get(3),qList.get(i), page);
								if(!tutorialPageList.isEmpty()) {
									break;
									
								}
								tutorialPageList=tutorialRepo.findByOutlineByQuerywords4(qList.get(1), qList.get(2), qList.get(3),qList.get(i), page);
								if(!tutorialPageList.isEmpty()) {
									break;
									
								}
							}
							
						
						}}
						
						if(tutorialPageList.isEmpty()) {
							for(int i=0; i<5; i++) {
								if(i==2 || i==3 || i==4) {
									tutorialPageList=tutorialRepo.findByOutlineByQuerywords3(qList.get(0), qList.get(1), qList.get(i), page);
									if(!tutorialPageList.isEmpty()) {
										break;
										
									}
								}
								if(i==3||i==4){
									tutorialPageList=tutorialRepo.findByOutlineByQuerywords3(qList.get(0), qList.get(2), qList.get(i), page);
									if(!tutorialPageList.isEmpty()) {
										break;
										
									}
									
									tutorialPageList=tutorialRepo.findByOutlineByQuerywords3(qList.get(1), qList.get(2), qList.get(i), page);
									if(!tutorialPageList.isEmpty()) {
										break;
										
									}
								}
								if(i==4) {
									
									tutorialPageList=tutorialRepo.findByOutlineByQuerywords3(qList.get(0), qList.get(3), qList.get(i), page);
									if(!tutorialPageList.isEmpty()) {
										break;
										
									}
									tutorialPageList=tutorialRepo.findByOutlineByQuerywords3(qList.get(1), qList.get(3), qList.get(i), page);
									if(!tutorialPageList.isEmpty()) {
										break;
										
									}
								}
							}
						}
						

						if(tutorialPageList.isEmpty()) {
							for(int i=0; i<5; i++) {
								if(i!=0) {
									tutorialPageList=tutorialRepo.findByOutlineByQuerywords2(qList.get(0),  qList.get(i), page);
									if(!tutorialPageList.isEmpty()) {
										break;
										
									}
									if(i!=1 ){
										tutorialPageList=tutorialRepo.findByOutlineByQuerywords2(qList.get(1),  qList.get(i), page);
										if(!tutorialPageList.isEmpty()) {
											break;
											
										}
										if(i!=2) {
											tutorialPageList=tutorialRepo.findByOutlineByQuerywords2(qList.get(2),  qList.get(i), page);
											if(!tutorialPageList.isEmpty()) {
												break;
												
											}
											if(i!=3) {
												tutorialPageList=tutorialRepo.findByOutlineByQuerywords2(qList.get(3),  qList.get(i), page);
												if(!tutorialPageList.isEmpty()) {
													break;
													
												}
										}
										
										}
										
										
									}
								}
								
								
							}
						}
					}
					
					
						
					
				
				
				if(queryLength==4) {
					
					tutorialPageList=tutorialRepo.findByOutlineByQuerywords4(qList.get(0), qList.get(1), qList.get(2),qList.get(3), page);
				
						
						if(tutorialPageList.isEmpty()) {
							for(int i=2; i<4; i++) {
								if(i==2 || i==3 ) {
									tutorialPageList=tutorialRepo.findByOutlineByQuerywords3(qList.get(0), qList.get(1), qList.get(i), page);
									if(!tutorialPageList.isEmpty()) {
										break;
										
									}
									if(i==2) {
										
										tutorialPageList=tutorialRepo.findByOutlineByQuerywords3(qList.get(1), qList.get(3), qList.get(i), page);
										if(!tutorialPageList.isEmpty()) {
											break;
											
										}
										
									}
									
									if(i==3) {
										tutorialPageList=tutorialRepo.findByOutlineByQuerywords3(qList.get(0), qList.get(2), qList.get(i), page);
										if(!tutorialPageList.isEmpty()) {
											break;
											
										}
										
										tutorialPageList=tutorialRepo.findByOutlineByQuerywords3(qList.get(1), qList.get(2), qList.get(i), page);
										if(!tutorialPageList.isEmpty()) {
											break;
											
										}
										
									}
									
								}
								
							}
						}
						

						if(tutorialPageList.isEmpty()) {
							for(int i=0; i<4; i++) {
								if(i!=0) {
									tutorialPageList=tutorialRepo.findByOutlineByQuerywords2(qList.get(0),  qList.get(i), page);
									if(!tutorialPageList.isEmpty()) {
										break;
										
									}
									if(i!=1 ){
										tutorialPageList=tutorialRepo.findByOutlineByQuerywords2(qList.get(1),  qList.get(i), page);
										if(!tutorialPageList.isEmpty()) {
											break;
											
										}
										if(i!=2) {
											tutorialPageList=tutorialRepo.findByOutlineByQuerywords2(qList.get(2),  qList.get(i), page);
											if(!tutorialPageList.isEmpty()) {
												break;
												
											}
											
										
										}
										
										
									}
								}
								
								
							}
						}
					
				}
				
				if(queryLength==3) {
					
					tutorialPageList=tutorialRepo.findByOutlineByQuerywords3(qList.get(0), qList.get(1), qList.get(2), page);
					if(tutorialPageList.isEmpty()) {
						for(int i=0; i<3; i++) {
							if(i!=0) {
								tutorialPageList=tutorialRepo.findByOutlineByQuerywords2(qList.get(0),  qList.get(i), page);
								if(!tutorialPageList.isEmpty()) {
									break;
									
								}
								if(i!=1 ){
									tutorialPageList=tutorialRepo.findByOutlineByQuerywords2(qList.get(1),  qList.get(i), page);
									if(!tutorialPageList.isEmpty()) {
										break;
										
									}
									
									
								}
							}
							
							
						}
					}
					
				}
				
				if(queryLength==2) {
					
					tutorialPageList=tutorialRepo.findByOutlineByQuerywords2(qList.get(0), qList.get(1), page);
					if(tutorialPageList.isEmpty()) {
						tutorialPageList=tutorialRepo.findByOutlineContainingIgnoreCase(qList.get(0), page);
						if(tutorialPageList.isEmpty()) {
						tutorialPageList=tutorialRepo.findByOutlineContainingIgnoreCase(qList.get(1), page);	
							
						}
					}
					
				}
				
			}
				
		      return tutorialPageList;
		}
		
	}
	
	public  Page<Tutorial> searchByWord2(String query, Pageable page){
		List<String> qList = new ArrayList<String>(Arrays.asList(query.split(" ")));
		Page<Tutorial> tutpage=tutorialRepo.findByOutlineByQueryPagination(query, page);
		
		if(!tutpage.isEmpty()) {
			return tutpage;
		}
		else {
			tutpage=tutorialRepo.findByOutlineByQuerywords2(qList.get(0), qList.get(1), page);
			return tutpage;
		}
	}

}
