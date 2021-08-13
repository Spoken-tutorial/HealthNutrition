package com.health.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.model.TraineeInformation;
import com.health.model.TrainingInformation;
import com.health.model.User;
import com.health.repository.TrainingInformationRespository;
import com.health.service.TrainingInformationService;

/**
 * Default implementation of the {@link com.health.service.TrainingInformationService} interface.  
 * @author om prakash soni
 * @version 1.0
 */
@Service
public class TrainingInformationServiceImpl implements TrainingInformationService {

	@Autowired
	private TrainingInformationRespository trainingInfoRepo;

	/**
	 * @see com.health.service.TrainingInformationService#getNewId()
	 */
	@Override
	public int getNewId() {
		// TODO Auto-generated method stub
		try {
			return trainingInfoRepo.getNewId()+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * @see com.health.service.TrainingInformationService#save(TrainingInformation)
	 */
	@Override
	public void save(TrainingInformation temp) {
		// TODO Auto-generated method stub
		trainingInfoRepo.save(temp);

	}

	/**
	 * @see com.health.service.TrainingInformationService#addTrainee(TrainingInformation, Set)
	 */
	@Override
	public void addTrainee(TrainingInformation training, Set<TraineeInformation> trainee) {
		// TODO Auto-generated method stub
		training.getTraineeInfos().addAll(trainee);
		trainingInfoRepo.save(training);
	}

	/**
	 * @see com.health.service.TrainingInformationService#findAll()
	 */
	@Override
	public List<TrainingInformation> findAll() {
		// TODO Auto-generated method stub
		return (List<TrainingInformation>) trainingInfoRepo.findAll();
	}

	/**
	 * @see com.health.service.TrainingInformationService#getById(int)
	 */
	@Override
	public TrainingInformation getById(int id) {
		// TODO Auto-generated method stub
		try {
			Optional<TrainingInformation> local = trainingInfoRepo.findById(id);
			return local.get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * @see com.health.service.TrainingInformationService#findByUser(User)
	 */
	@Override
	public List<TrainingInformation> findByUser(User user) {
		// TODO Auto-generated method stub
		return trainingInfoRepo.findByUser(user);
	}





}