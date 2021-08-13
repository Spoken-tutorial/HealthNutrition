package com.health.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.TraineeInformation;
import com.health.model.TrainingInformation;
import com.health.repository.TraineeInformationRepository;
import com.health.service.TraineeInformationService;

/**
 * Default implementation of the {@link com.health.service.TraineeInformationService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class TraineeInformationServiceImpl implements TraineeInformationService{

	@Autowired
	private TraineeInformationRepository repo;
	
	/**
	 * @see com.health.service.TraineeInformationService#getNewId()
	 */
	@Override
	public int getNewId() {
		// TODO Auto-generated method stub
		try {
			return repo.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * @see com.health.service.TraineeInformationService#findAll()
	 */
	@Override
	public List<TraineeInformation> findAll() {
		// TODO Auto-generated method stub
		return (List<TraineeInformation>) repo.findAll();
	}

	/**
	 * @see com.health.service.TraineeInformationService#findAllBytraineeInfos(TrainingInformation)
	 */
	@Override
	public List<TraineeInformation> findAllBytraineeInfos(TrainingInformation trainingId) {
		// TODO Auto-generated method stub
		return repo.findAllBytraineeInfos(trainingId);
	}

	/**
	 * @see com.health.service.TraineeInformationService#findById(int)
	 */
	@Override
	public TraineeInformation findById(int traineeId) {
		// TODO Auto-generated method stub
		try {
			return repo.findById(traineeId).get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @see com.health.service.TraineeInformationService#save(TraineeInformation)
	 */
	@Override
	public void save(TraineeInformation temp) {
		// TODO Auto-generated method stub
		repo.save(temp);
	}




}
