
//var Health="/HealthNutrition";

$(document).ready(function() {
			$('.pending-upload').tooltip({ title: 'Pending' });
			$('.admin-review').tooltip({ title: 'Waiting for Admin Review' });
			$('.domain-review').tooltip({ title: 'Waiting for Domain Review' });
			$('.quality-review').tooltip({ title: 'Waiting for Quality Review' });
			$('.review-accepted').tooltip({ title: 'Accepted' });
			$('.review-improvement').tooltip({ title: 'Need Improvement' });
			$('.not-required').tooltip({ title: 'Not Required' });
			
			
			   $('a[data-toggle="tab"]').on('show.bs.tab', function(e) {
			        localStorage.setItem('activeTab', $(e.target).attr('href'));
			    });
			    var activeTab = localStorage.getItem('activeTab');
			    if(activeTab){
			        $('#myTab a[href="' + activeTab + '"]').tab('show');
			    }
			    
/********************* Chnages made by om prakash ************************************************/
			    
			    $('.catStat').change(function() {
					
  					var catName=$(this).find(":selected").val();

						$.ajax({
							type : "GET",
							url : projectPath+"tutCountOnCat",
							data : {
								"id" : catName
							},
							contentType : "application/json",
							success : function(result) {
							
								$('#onChangedataStat').html(result);
								$('#statBox').hide();
							},

							error : function(err) {
								console.log("not working. ERROR: "+ JSON.stringify(err));
								var result = "Error";
								showStatus(ERROR,result);
							}
						});

					});
			    
			    
			     $('.lanStat').change(function() {
					
  					var lanName=$(this).find(":selected").val();

						$.ajax({
							type : "GET",
							url : projectPath+"tutCountOnLan",
							data : {
								"id" : lanName
							},
							contentType : "application/json",
							success : function(result) {
							
								$('#onChangedataStat').html(result);
								$('#statBox').hide();
							},

							error : function(err) {
								console.log("not working. ERROR: "+ JSON.stringify(err));
								var result = "Error";
								showStatus(ERROR,result);
							}
						});

					});
					
					
			    
			    $(".timeScriptFetchData").click(function(){
  					var tut_id=$(this).attr('value');
  						
  					$('#tutorialId').prop('value',tut_id);
  					$('#uploadTimescript').modal('show');
  				}) 
  				
  				
  				$('#timeScript').click(function() {
  					
  					var TutorialID=$("#tutorialId").val();

						var form = $('#upload-file-form-script')[0];
						var formData = new FormData(form);
						formData.append('id',TutorialID);

						$.ajax({
							type : "POST",
							url : projectPath+"addTimeScript",
							data : formData,
							enctype : 'multipart/form-data',
							processData : false,
							contentType : false,
							cache : false,
							success : function(result) {
							
								$('#viewScript').html(result);
						
								var result = "Script updated successfully";
								showStatus(SUCCESS,result);
								$('.upload-status').hide();
							},

							error : function(err) {
								console.log("not working. ERROR: "+ JSON.stringify(err));
								var result = "Error";
								showStatus(ERROR,result);
							}
						});

					});
			    
			    
			    
/**********************************end***********************************************************/
			
			
/******************* Changes made by om prakash *************************************/
			
		$('.enableBrouchure').click(function() {
				
				var test_id=$(this).attr('value');
				
				
				$('#Success').css({"display": "none"});
				$('#Failure').css({"display": "none"});
			
				$.ajax({
					type : "GET",
					url : projectPath+"enableDisableBrouchure",
					data : {
						"id" : test_id
					},
					contentType : "application/json",
					success : function(data) {
						if(data){
			   				$('#'+test_id).addClass('fas fa-times-circle');
			   				$('#'+test_id).removeClass('fas fa-check-circle');
			   				$('#'+test_id).css({"color": "red"});
			   				$('#Success').css({"display": "block"});
			   	
			   			}else{
			   				$('#Failure').css({"display": "block"});
			   			}
					},

					error : function(err) {
						console.log("not working. ERROR: "+ JSON.stringify(err));
					}

				});

			});
			
			
			$('.disableBrouchure').click(function() {
				
				var test_id=$(this).attr('value');
				
				$('#Success').css({"display": "none"});
				$('#Failure').css({"display": "none"});
			
				$.ajax({
					type : "GET",
					url : projectPath+"enableDisableBrouchure",
					data : {
						"id" : test_id
					},
					contentType : "application/json",
					success : function(data) {
						if(data){
			   				
			   				$('#'+test_id).addClass('fas fa-check-circle');
			   				$('#'+test_id).removeClass('fas fa-times-circle');
			   				$('#'+test_id).css({"color": "green"});
			   				$('#Success').css({"display": "block"});
			   				
			   	
			   			}else{
			   				$('#Failure').css({"display": "block"});
			   			}
					},

					error : function(err) {
						console.log("not working. ERROR: "+ JSON.stringify(err));
					}

				});

			});
			
			
			$('.enableTest').click(function() {
				
				var test_id=$(this).attr('value');
				
				
				$('#Success').css({"display": "none"});
				$('#Failure').css({"display": "none"});
			
				$.ajax({
					type : "GET",
					url : projectPath+"enableDisableTestimonial",
					data : {
						"id" : test_id
					},
					contentType : "application/json",
					success : function(data) {
						if(data){
			   				$('#'+test_id).addClass('fas fa-times-circle');
			   				$('#'+test_id).removeClass('fas fa-check-circle');
			   				$('#'+test_id).css({"color": "red"});
			   				$('#Success').css({"display": "block"});
			   	
			   			}else{
			   				$('#Failure').css({"display": "block"});
			   			}
					},

					error : function(err) {
						console.log("not working. ERROR: "+ JSON.stringify(err));
					}

				});

			});
			
			
			$('.disableTest').click(function() {
				
				var test_id=$(this).attr('value');
				
				$('#Success').css({"display": "none"});
				$('#Failure').css({"display": "none"});
			
				$.ajax({
					type : "GET",
					url : projectPath+"enableDisableTestimonial",
					data : {
						"id" : test_id
					},
					contentType : "application/json",
					success : function(data) {
						if(data){
			   				
			   				$('#'+test_id).addClass('fas fa-check-circle');
			   				$('#'+test_id).removeClass('fas fa-times-circle');
			   				$('#'+test_id).css({"color": "green"});
			   				$('#Success').css({"display": "block"});
			   				
			   	
			   			}else{
			   				$('#Failure').css({"display": "block"});
			   			}
					},

					error : function(err) {
						console.log("not working. ERROR: "+ JSON.stringify(err));
					}

				});

			});
			
			
		/******************* end ****************************************************/
		/******************* Changes made by om prakash *************************************/
			
			$('.enableConsult').click(function() {
				
				var consult_id=$(this).attr('value');
				
				
				$('#Success').css({"display": "none"});
				$('#Failure').css({"display": "none"});
			
				$.ajax({
					type : "GET",
					url : projectPath+"enableDisableConsultant",
					data : {
						"id" : consult_id
					},
					contentType : "application/json",
					success : function(data) {
						if(data){
			   				$('#'+consult_id).addClass('fas fa-times-circle');
			   				$('#'+consult_id).removeClass('fas fa-check-circle');
			   				$('#'+consult_id).css({"color": "red"});
			   				$('#Success').css({"display": "block"});
			   	
			   			}else{
			   				$('#Failure').css({"display": "block"});
			   			}
					},

					error : function(err) {
						console.log("not working. ERROR: "+ JSON.stringify(err));
					}

				});

			});
			
			
			$('.disableConsult').click(function() {
				
				var consult_id=$(this).attr('value');
				
				$('#Success').css({"display": "none"});
				$('#Failure').css({"display": "none"});
			
				$.ajax({
					type : "GET",
					url : projectPath+"enableDisableConsultant",
					data : {
						"id" : consult_id
					},
					contentType : "application/json",
					success : function(data) {
						if(data){
			   				
			   				$('#'+consult_id).addClass('fas fa-check-circle');
			   				$('#'+consult_id).removeClass('fas fa-times-circle');
			   				$('#'+consult_id).css({"color": "green"});
			   				$('#Success').css({"display": "block"});
			   				
			   	
			   			}else{
			   				$('#Failure').css({"display": "block"});
			   			}
					},

					error : function(err) {
						console.log("not working. ERROR: "+ JSON.stringify(err));
					}

				});

			});
			
			
		/******************* end ****************************************************/
			
			$('#profilePicture').change(function(){
				
				readImageUrl(this);
				$("#chngProfilePic").prop('disabled', false);
			})
			
			
			$('#chngProfilePic').click(function(){
				
				event.preventDefault();
				
				updateProfilePicture();
				
			})
			
			$('#searchTrainees').click(function() {
				
				var traineeId = $("#eventName").find(":selected").val();
			
				$.ajax({
					type : "GET",
					url : projectPath+"loadTraineeByTrainingId",
					data : {
						"id" : traineeId
					},
					contentType : "application/json",
					success : function(result) {
						html = '';
						$.each(result,function(key,value){
						      html +='<tr>';
						      html +='<td>'+ value.name + '</td>';
						      html +='<td>'+ value.age + '</td>';
						      html +='<td>'+ value.aadhar + '</td>';
						      html +='<td>'+ value.phone + '</td>';
						      html +='<td>'+ value.organization + '</td>';
						      html +='<td>'+ value.email + '</td>';
						      html +='<td>'+ value.gender + '</td>';
						      html +='</tr>';
						  });
						
						$('#tableBody').html(html);
					},

					error : function(err) {
						console.log("not working. ERROR: "+ JSON.stringify(err));
					}

				});

			});

			
			$('#newPassTeacher').change(function(){
  				
  				$('#updatePasswordTeacher').prop('disabled',true);
  				var confpass=$('#confPassTeacher').val();
  				var pass=$(this).val();
  				if(confpass.length>0 && pass.length>0){
  					$('#updatePasswordTeacher').prop('disabled',false);
  					
  				}
  			
  			})
  			
  			$('#confPassTeacher').change(function(){
  				
  				$('#updatePasswordTeacher').prop('disabled',true);
  				var confpass=$('#newPassTeacher').val();
  				var pass=$(this).val();
  				if(confpass.length>0 && pass.length>0){
  					$('#updatePasswordTeacher').prop('disabled',false);
  					
  				}
  			
  			})
  				
  			$('#updatePasswordTeacher').click(function(){
  			
  			var currPass=$('#currentPasswordTeacher').val();	
  			var pass=$('#newPassTeacher').val();
  			var confpass=$('#confPassTeacher').val();
  			
			
			var urlPassed;
		
        	urlPassed= projectPath+"updatePassword";
		
        
  			if(pass === confpass){
  				
  				var passwordData={
  					"password":pass,
  					"currentPassword":currPass
  					
  				};
  				
  				
  				$.ajax({
  					type: "GET",
  					contentType: "application/json",
  					url: urlPassed,
  					data:passwordData,
  					cache:false,
  					timeout: 600000,
  					success:function(data){
  						 
  						 $('#Success').css({"display": "none"}); 
  						 $('#FailurePassMismatch').css({"display": "none"});
  						 $('#FailureCurPassWrong').css({"display": "none"});
  						 $('#lengthIncorrect').css({"display": "none"});
  						
  						 if(data[0]==="Success"){
  							 $('#Success').css({"display": "block"});
  							 $('#newPassTeacher').prop('value',"");
  							 $('#confPassTeacher').prop('value',"");
  							 $('#currentPasswordTeacher').prop('value',"");
  							 $('#updatePasswordTeacher').prop('disabled',true);
  							 
  							 setTimeout(function() {
  					            $('#Success').fadeOut(1000)}, 4000);
  						 }else if(data[0]==="failure"){
  							 $('#FailureCurPassWrong').css({"display": "block"});
  							 $('#newPassTeacher').prop('value',"");
 							 $('#confPassTeacher').prop('value',"");
 							 $('#currentPasswordTeacher').prop('value',"");
 							 $('#updatePasswordTeacher').prop('disabled',true);
 							 
 							 setTimeout(function() {
 					            $('#FailureCurPassWrong').fadeOut(1000)}, 4000);
  						 }else if(data[0]==="passwordLengthError"){
  							 
  							 $('#lengthIncorrect').css({"display": "block"});
  							 $('#newPassTeacher').prop('value',"");
 							 $('#confPassTeacher').prop('value',"");
 							 $('#currentPasswordTeacher').prop('value',"");
 							 $('#updatePasswordTeacher').prop('disabled',true);
 							 
 							 setTimeout(function() {
 					            $('#lengthIncorrect').fadeOut(1000)}, 4000);
  							 
  						 }
  						
  					
  					},
  				
  				error : function(err){
  					console.log("not working. ERROR: "+JSON.stringify(err));
  				}
  				
  				});
  				
  				
  			}else{
  				 $('#Success').css({"display": "none"}); 
  				 $('#FailurePassMismatch').css({"display": "none"});
				 $('#FailureCurPassWrong').css({"display": "none"});
				 $('#FailurePassMismatch').css({"display": "block"});
			
				 setTimeout(function() {
			            $('#FailurePassMismatch').fadeOut(1000)}, 4000);
			  
				 
				 $('#newPassTeacher').prop('value',"");
				 $('#confPassTeacher').prop('value',"");
				 $('#currentPasswordTeacher').prop('value',"");
				 
				 $('#updatePasswordTeacher').prop('disabled',true);
  				
  			}
  			
  		})
  		
	// JQUERY AJAX CALL TO TAKE CONTACT DATA FROM USER SIDE ----------------------------------------
			
			
			$('#name').change(function(){
				var name=$('#name').val();
				var email=$('#email').val();
				var desc=$('#message').val();
	  			
	  			
	  			$("#contactForm").prop('disabled', true);
				
	  			if(name.length>0&& email.length>0 && desc.length>0){
	  				$("#contactForm").prop('disabled', false);
	  			}
	  			
	  			
	  		})
	  		
	  		$('#email').change(function(){
				var name=$('#name').val();
				var email=$('#email').val();
				var desc=$('#message').val();
	  			
	  			
	  			$("#contactForm").prop('disabled', true);
				
	  			if(name.length>0&& email.length>0 && desc.length>0){
	  				$("#contactForm").prop('disabled', false);
	  			}
	  			
	  			
	  		})
	  		
	  		$('#message').change(function(){
				var name=$('#name').val();
				var email=$('#email').val();
				var desc=$('#message').val();
	  			
	  			
	  			$("#contactForm").prop('disabled', true);
				
	  			if(name.length>0&& email.length>0 && desc.length>0){
	  				$("#contactForm").prop('disabled', false);
	  			}
	  			
	  			
	  		})
			
	  		
		
			$('#contactForm').click(function(){
				var name=$('#name').val();
				var email=$('#email').val();
				var desc=$('#message').val();
				if(name.length>0 && validateEmail(email) && desc.length>0){
					
					var json={
							"name":name,
							"message":desc,
							"email":email,
					};
					var jsdata= JSON.stringify(json);
					var urlPassed;
					
					urlPassed= projectPath+"addContactForm";
				
					
					$.ajax({
					  	 type: "POST",
			        	 contentType: "application/json",
			       		 url: urlPassed,
			       		 data: JSON.stringify(json),
			       		 dataType: 'json',
			       		 cache: false,
			        	 timeout: 600000,
			        	
			       		 success: function (data){
			       			 
			       			 
			       			$('#statusOnContactForm').css({"display": "none"}); 
							  
							 $('#statusOnContactFormAfterAjaxCallSucess').css({"display": "none"});
							 $('#statusOnContactFormAfterAjaxCallFailure').css({"display": "none"});
							
							 if(data[0]==="Success"){
								 $('#statusOnContactFormAfterAjaxCallSucess').css({"display": "block"});
								 setTimeout(function() {
							            $('#statusOnContactFormAfterAjaxCallSucess').fadeOut(1000)}, 4000);
							 }else {
								 $('#statusOnContactFormAfterAjaxCallFailure').css({"display": "block"});
								 setTimeout(function() {
							            $('#statusOnContactFormAfterAjaxCallFailure').fadeOut(1000)}, 4000);
							 }
							 
							 $("#name").prop('value', "");
							 $("#email").prop('value', "");
							 $("#message").prop('value', "");
							 
							 setTimeout(function() {
						            $('#Failure').fadeOut(1000)}, 4000);
							 
						},
						
						error : function(err){
							console.log("not working. ERROR: "+JSON.stringify(err));
						}
						
						
					});
					
					
				}else{
					$('#statusOnContactForm').css({"display": "block"});
				}
				
			});
			

			/*--------------- constants ---------------*/
			const SUCCESS = 1;
			const ERROR = 0;

			/*--------------- constants ---------------*/
			// retrieve latest tab shown before refresh
			var activeTab = localStorage.getItem('activeTab');
			if (activeTab) {
				$('#nav-tab a[href="#' + activeTab + '"]').tab('show');
				localStorage.setItem('activeTab', "");
				$('.approve-success-msg').text(
						localStorage.getItem('msg'));
				localStorage.setItem('msg', "");
			}

			/* master trainer */

			var activeTab = localStorage.getItem('activeTab');
			if (activeTab) {
				$('#v-pills-tab a[href="#' + activeTab + '"]').tab(
				'show');
				localStorage.setItem('activeTab', "");
				$('.tab-pane').text(localStorage.getItem('msg'));
				localStorage.setItem('msg', "");
			}

			function showStatus(status, msg) {
				// status : 1 - success , 0 - failure
				$('.alert-msg').show();
				$('.alert-msg').html(msg);
				if (status) {
					$('.alert-msg').addClass('alert-success');
				} else {
					$('.alert-msg').addClass('alert-danger');
				}

			}
			
			$('.modal-status').on('hidden.bs.modal', function() {
				$('.alert-msg').hide();

			});

			$('#OutlineAcceptOrNeedTiImprovemenet').on(
					'hidden.bs.modal', function() {
						location.reload();

					});

			$('#approveContributorId').on('show.bs.tab', function() {
				location.reload();

			});

			$('#KeywordStatusAccept').on('hidden.bs.modal', function() {
				location.reload();

			});

			$('#videoViewIdAdmin').on('hidden.bs.modal', function() {
				location.reload();

			});

			$('#videoViewIdDomain').on('hidden.bs.modal', function() {
				location.reload();

			});

			$('#VideoStatusAccept').on('hidden.bs.modal', function() {
				location.reload();

			});

			$('#scriptStatusAcceptDomain').on('hidden.bs.modal',
					function() {
				location.reload();

			});

			$('#VideoStatusAccept').on('hidden.bs.modal', function() {
				location.reload();

			});
			
			$('#prerequisiteModale').on('hidden.bs.modal', function() {
				location.reload();

			});

			$('.nav-item').on(
					'click',
					function() {
						localStorage.setItem('msg', "");
						$('.approve-success-msg').text(
								localStorage.getItem('msg'));
					});
			
			// domain
			$('#SlideStatusAccept').on('hidden.bs.modal', function() {
				location.reload();

			});
			$('#keywordModaleViewInDomain').on('hidden.bs.modal', function() {
				location.reload();
			});
			$('#PreViewIdDomain').on('hidden.bs.modal', function() {
				location.reload();
			});
			

			// Contributor

			// here is code for keyword review
			$('#keywordModale').on('hidden.bs.modal', function() {
				location.reload();

			});

			// here is code for Script
			$('#scriptModale').on('hidden.bs.modal', function() {
				location.reload();

			});

			// Here is code for
			$('#slideModale').on('hidden.bs.modal', function() {
				location.reload();

			});

			$('#videoModel').on('hidden.bs.modal', function() {
				location.reload();

			});

			// Admin

			$('#VideoStatusAccept').on('hidden.bs.modal', function() {
				location.reload();

			});

			// Quality

			$('#outlineViewModelDomain').on(
					'hidden.bs.modal', function() {
						location.reload();

					});

			$('#scriptStatusAccept').on('hidden.bs.modal', function() {
				location.reload();

			});

			$('#slideAcceptOrNeedImp').on('hidden.bs.modal',
					function() {
				location.reload();

			});

			$('#VideoAccepOrNeedToImprovementQuality').on(
					'hidden.bs.modal', function() {
						location.reload();

					});

			$('#keywordModaleViewInQuality').on(
					'hidden.bs.modal', function() {
						location.reload();

					});
			
			$('#PreViewQualityId').on(
					'hidden.bs.modal', function() {
						location.reload();

					});

			$('#categoryNameList').change(function() {

			});

			// Here is code for category display into category and
			// language

			$('#categoryId')
			.change(
					function() {
						var categoryid = $("#categoryNameList")
						.val();
						var languageid = $("#inputLanguageList")
						.val();

						$
						.ajax({

							type : "GET",
							url : projectPath+ "loadCategoryAndLanguage",
							data : {
								"id" : categoryid,
								"lanid" : language
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select language</option>';
								for (var i = 0; i < len; i++) {

									html += '<option value="'+ result[i]+ '">'+ result[i]+ '</option>';
								}
								html += '</option>';

								$("#inputLanguageList").prop('disabled',false);
								$('#inputLanguageList').html(html);
							},

							error : function(err) {
								console.log("not working. ERROR: "+ JSON.stringify(err));
							}
						});
					});


			// Here is code for display category according to language

			$('#languageContributerId')
			.change(

					function() {

						var languageid = $("#languageContributerId").val();

						$.ajax({

							type : "GET",
							url  : projectPath+"loadcategoryBylanguage",

							data : {

								"languageid" : languageid,

							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select Category</option>';
								for (var i = 0; i < len; i++) {

									html += '<option value="'+ result[i]+ '">'+ result[i]+ '</option>';
								}
								html += '</option>';

								$("#categoryDomainId").prop('disabled',false);
								$('#categoryDomainId').html(html);
							},

							error : function(err) {
								console.log("not working. ERROR: "+ JSON.stringify(err));
							}
						});
					});




//			$('#categoryname').change(function() {
//			alert('clicked');
//			var categoryId = $(this).val();
//			console.log(categoryId);
//			$
//			.ajax({
//			type : "GET",
//			url : "/listTopicsByCategory",
//			data : {
//			"category" : categoryId
//			},
//			contentType : "application/json",
//			success : function(result) {
//			console.log(result);
//			var html = '';
//			var len = result.length;
//			html += '<option value="0">Select language</option>';
//			for (var i = 0; i < len; i++) {

//			html += '<option value="'
//			+ result[i]
//			+ '">'
//			+ result[i]
//			+ '</option>';
//			}
//			html += '</option>';

//			$("#inputTopic").prop('disabled',false);
//			$('#inputTopic').html(html);

//			},
//			error : function(err) {
//			console.log("not working. ERROR: "+ JSON.stringify(err));
//			}

//			});

//			});


			/* here we write code for calling Topic */

			$('#categoryId')
			.change(
					function() {

						var catgoryid = $(this).find(
								":selected").val();
						$
						.ajax({
							type : "GET",
							url :  projectPath+"loadByCategoryTuturialTopic",
							data : {
								"id" : catgoryid
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select Topic</option>';
								for (var i = 0; i < len; i++) {
									html += '<option value="'
										+ result[i]
									+ '">'
									+ result[i]
									+ '</option>';
								}
								html += '</option>';

								$("#inputTopic").prop(
										'disabled',
										false);
								$('#inputTopic').html(
										html);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			/* here we write code for calling language */

			$('#categoryId')
			.change(
					function() {

						var catgoryid = $(this).find(
								":selected").val();
						$
						.ajax({

							type : "GET",
							url :  projectPath+"loadByCategoryTuturial",
							data : {
								"id" : catgoryid
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select language</option>';
								for (var i = 0; i < len; i++) {
									html += '<option value="'
										+ result[i]
									+ '">'
									+ result[i]
									+ '</option>';
								}
								html += '</option>';

								$("#inputLanguage")
								.prop(
										'disabled',
										false);
								$('#inputLanguage')
								.html(html);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			// });
			//		
			//			  
			// });

			/* Here is code for select District */
/****************************** changes made by om prakash ****************************************/
			
			$('#stateNameId').change(function() {

						var state = $(this).find(":selected").val();

						$.ajax({
							type : "GET",
							url : projectPath+"loadDistrictByState",
							data : {
								"id" : state
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select District</option>';
	  	  			            $.each(result , function( key, value ) {
		  	  			        html += '<option value=' + key + '>'
		  			               + value
		  			               + '</option>';
		  	  			        })
	  	  			            html += '</option>';

								$("#districtId").prop('disabled',false);
								$('#districtId').html(html);

							},

							error : function(err) {
								console.log("not working. ERROR: "+ JSON.stringify(err));
							}

						});

					});

			/*
			 * Here is code for selection of title name according to
			 * category name
			 */
/******************** CHANGES MADE BYU OM PRAKASH SONI ******************************/
			
			$('#catMasPostId').change(function() {
				
				var catId = $(this).find(":selected").val();

				$.ajax({
					type : "GET",
					url : projectPath+"loadTitleNameInMasterTraining",
					data : {
						"id" : catId
					},
					contentType : "application/json",
					success : function(result) {

						var html = '';
						var len = result.length;
						html += '<option value="0">Select Training</option>';
						$.each(result , function( key, value ) {
		  	  			        html += '<option value=' + key + '>'
		  			               + value
		  			               + '</option>';
		  	  			})
	  	  			     html += '</option>';
						 	

						$("#postTrainingdataId").prop('disabled',false);
						$('#postTrainingdataId').html(html);

					},

					error : function(err) {
						console.log("not working. ERROR: "+ JSON.stringify(err));
					}

				});

			});
			
			$('#catMasId').change(function() {
						
						var catId = $(this).find(":selected").val();

						$.ajax({
							type : "GET",
							url : projectPath+"loadTitleNameInMasterTraining",
							data : {
								"id" : catId
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select Training</option>';
								$.each(result , function( key, value ) {
				  	  			        html += '<option value=' + key + '>'
				  			               + value
				  			               + '</option>';
				  	  			})
			  	  			     html += '</option>';
								 	

								$("#feedbackmasterId").prop('disabled',false);
								$('#feedbackmasterId').html(html);

							},

							error : function(err) {
								console.log("not working. ERROR: "+ JSON.stringify(err));
							}

						});

					});
			
			$('#eventCategory').change(function() {
				
				var catId = $(this).find(":selected").val();
			
				$.ajax({
					type : "GET",
					url : projectPath+"loadTitleNameInMasterTraining",
					data : {
						"id" : catId
					},
					contentType : "application/json",
					success : function(result) {

						var html = '';
						var len = result.length;
						html += '<option value="0">Select Training</option>';
						$.each(result , function( key, value ) {
		  	  			        html += '<option value=' + key + '>'
		  			               + value
		  			               + '</option>';
		  	  			})
	  	  			     html += '</option>';
						 	

						$("#eventName").prop('disabled',false);
						$('#eventName').html(html);

					},

					error : function(err) {
						console.log("not working. ERROR: "+ JSON.stringify(err));
					}

				});

			});

			
			
			// Changes made by Om prakash

			$('#preRequsiteId').change(function() {
			
				var catgoryid = $(this).find(":selected").val();
				var tutorialId=$('#tutorialId').val();
				var langName=$('#lanId').val();

						$.ajax({
							type : "GET",
							url : projectPath+"loadTopicByCategoryPreRequistic",
							data : {

								"id" : catgoryid,
								"tutorialId" : tutorialId,
								"langName" : langName
							},
							contentType : "application/json",
							success : function(result) 
							{


								var html = '';
								var len = result.length;
								html += '<option value="0">Select Topic</option>';
								 $.each(result , function( key, value ) {
				  	  			        html += '<option value=' + key + '>'
				  			               + value
				  			               + '</option>';
				  	  			        })
								
								html += '</option>';



								$("#inputLanguageAll").prop('disabled',false);
								$('#inputLanguageAll').html(html);

							},

							error : function(err) {
								console.log("not working. ERROR: "+ JSON.stringify(err));
							}

						});

					});



			$('#preAcceptDomain').change(function() {

				var vals = $("#preAcceptDomain").val();

				if (vals === '2') {

					$('#preNeedImprovement').show();

				}else{

					$('#preNeedImprovement').hide();

				}

			});


			$('#preAcceptQuality').change(function() {

				var vals = $("#preAcceptQuality").val();

				if (vals === '2') 
				{
					$('#preNeedImprovementQuality').show();

				}else{

					$('#preNeedImprovementQuality').hide();

				}

			});



			/*   Here is code for comment on prerequsite on Domain Reviwer side   */
/****************** changes made by Om Prakash *******************************/
			

			$('#preAcceptOrNeedToImprovemenetByDomain').click(function() {

						
						var tutorialId = $("#tutorialId").val();
						var preCommentMsg = $("#preCommentMsg").val();


//						alert(preCommentMsg);

						var vals = $("#preAcceptDomain").val();

						if (vals == '0') 
						{
							alert("Select Accept Or Need To Improvement");

						} else if (vals == '1') {

							$.ajax({

								type : "GET",
								url : projectPath + "acceptDomainPreRequistic",
								data : {
									"id" : tutorialId
								},
								contentType : "application/json",
								success : function(
										result) {
									showStatus(SUCCESS, result);
//									$("#statusVideoByDomain").prop('disabled',false);
//									$('#statusVideoByDomain').html(result);

								},

								error : function(err) {
									console.log("not working. ERROR: "+ JSON.stringify(err));
									result = "Error";
									showStatus(ERROR, result);
								}
							});

						} else if (vals == '2') {


							$
							.ajax({

								type : "GET",
								url : projectPath+"commentByReviewer",
								data : {
									"id" : tutorialId,
									"msg":preCommentMsg,
									"type" : "Pre_requistic",
									"role" : "Domain"
								},
								contentType : "application/json",
								success : function(
										result) {
//									$("#statusVideoByDomain").prop('disabled',false);
//									$('#statusVideoByDomain').html(result);
									showStatus(SUCCESS, result);

								},

								error : function(err) {
									console.log("not working. ERROR: "+ JSON.stringify(err));
									result = "Error";
									showStatus(ERROR, result);
								}

							});

						}

					});


			/*		Here is code for Quality Reviwer prerequisite */
/************** changes made by om prakash *******************************/
			
			$('#preAcceptOrNeedToImprovemenetByQuality').click(function() {

						
						var tutorialId = $("#tutorialId").val();
						var msg = $("#preCommentMsgTest").val();


						var vals = $("#preAcceptQuality").val();

						if (vals == '0') 
						{
							alert("Select Accept Or Need To Improvement");

						} else if (vals == '1') {

							$.ajax({

								type : "GET",
								url : projectPath+"acceptQualityPreRequistic",
								data : {
									"id" : tutorialId

								},
								contentType : "application/json",
								success : function(result) {
									showStatus(SUCCESS, result);


								},

								error : function(err) {
									console.log("not working. ERROR: "+ JSON.stringify(err));
									result = "Error";
									showStatus(ERROR, result);
								}
							});

						} else if (vals == '2') {


							$
							.ajax({

								type : "GET",
								url : projectPath+"commentByReviewer",
								data : 
								{
									"id" : tutorialId,
									"msg" : msg,
									"type" : "Pre_requistic",
									"role" : "Quality"
								},
								contentType : "application/json",
								success : function(
										result) {
//									$("#statusVideoByDomain").prop('disabled',false);
//									$('#statusVideoByDomain').html(result);
									showStatus(SUCCESS, result);

								},

								error : function(err) {
									console.log("not working. ERROR: "+ JSON.stringify(err));
									result = "Error";
									showStatus(ERROR, result);
								}

							});

						}

					});



			// Changes made by om prakash

			$('#uploadpreRequsiteId').click(function() {
//				var tutorialId = $("#inputLanguageAll").val();
				var tutorialId = $("#tutorialId").val();
				var categoryid = $("#categoryId").val();
//				var topicid = $("#topicID").val();
				var topicid = $("#inputLanguageAll").val();
				
				var lanId = $("#lanId").val();

				$.ajax({
					type : "GET",
					url : projectPath+"addPreRequistic",
					data : {

						"id" :tutorialId,
						"categoryname" : categoryid,
						"topicid" : topicid,
						"lanId" : lanId
					},
					contentType : "application/json",
					success : function(result) 
					{
						//$("#exampleModalLabelPre").prop('disabled',false);
						//$("#exampleModalLabelPre").html("Result");
						
						var msg = 'Pre-Requistic updated successfully.'
									$('#statusPreReq').addClass('d-block');
								$('#statusPreReq').html(msg);
						$.each(result , function( key, value ) {
			  	  			        if(key == 1){
										$('#status_pr').addClass('d-block');
										$('#status_pr').addClass('alert-success');
										$('#status_pr').html(value);
										
									}else{
										$('#status_pr').addClass('d-block');
								 		$('#status_pr').addClass('alert-danger');
										$('#status_pr').html(value);
									}
			  	  			     })

					},

					error : function(err) {
						alert('error uploadpreRequsiteId');
						console.log("error");
						console
						.log("not working. ERROR: "+ JSON.stringify(err));
						
						var msg = 'Error in updating Pre-Requistic.'
									// $('#statusOutline').html(html);
									$('#statusPreReq').addClass('d-block');
								 $('#statusPreReq').addClass('alert-danger');

								$('#statusPreReq').html(msg);
					}

				});

					});



			/*Here is code for feedbak for user on Home page*/
			$('#submitForUser').click(function() 
					{

				var firstName = $("#firstName").val();

				var email = $("#email").val();

				var subjectName = $("#subjectName").val();

				var msgForm = $("#msgForm").val();



				$.ajax({
					type : "GET",
					url : projectPath+"feedbackForUser",
					data : {

						"firstName" : firstName,
						"email": email,
						"subjectName": subjectName,
						"msgForm":msgForm,
					},
					contentType : "application/json",
					success : function(result) 
					{

						$("#exampleModalLabelKeyword")
						.prop(
								'disabled',
								false);
						$("#exampleModalLabelKeyword")
						.html(html);

					},

					error : function(err) {
						console
						.log("not working. ERROR: "
								+ JSON
								.stringify(err));
					}

				});

					});







			/*
			 * Here is code for selection of title name according to
			 * category name View csv file recored
			 */

			$('#catSelectedId')
			.change(
					function() {

						var state = $(this).find(":selected")
						.val();

						$
						.ajax({
							type : "GET",
							url : projectPath+"loadBycategoryInFeedb",
							data : {
								"id" : state
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select Title</option>';
								for (var i = 0; i < len; i++) {
									html += '<option value="'
										+ result[i]
									+ '">'
									+ result[i]
									+ '</option>';
								}
								html += '</option>';

								$("#titleNameId").prop(
										'disabled',
										false);
								$('#titleNameId').html(
										html);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			/* Here is code for distrcit Selection for adding city */
			$('#stateDistrictId')
			.change(
					function() {

						var state = $(this).find(":selected")
						.val();

						$
						.ajax({
							type : "GET",
							url : projectPath+"loadDistrictByState",
							data : {
								"id" : state
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select District</option>';
								for (var i = 0; i < len; i++) {
									html += '<option value="'
										+ result[i]
									+ '">'
									+ result[i]
									+ '</option>';
								}
								html += '</option>';

								$("#districtCityId")
								.prop(
										'disabled',
										false);
								$('#districtCityId')
								.html(html);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

	/*********************** changes made by om prakash ********************************/
			
			$('#districtId').change(function() {

						var dist = $(this).find(":selected").val();

						$.ajax({
							type : "GET",
							url : projectPath+"loadCityByDistrict",
							data : {
								"id" : dist
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select City</option>';
	  	  			            $.each(result , function( key, value ) {
		  	  			        html += '<option value=' + key + '>'
		  			               + value
		  			               + '</option>';
		  	  			        })
	  	  			            html += '</option>';

								$("#cityId").prop('disabled',false);
								$('#cityId').html(html);

							},

							error : function(err) {
								console.log("not working. ERROR: "+ JSON.stringify(err));
							}

						});

					});

			/*
			 * Here is code for access topic according to category
			 * master trainer
			 */

			// $("#catMasterId").change(function(){
			// 2a7c4fbe32196d9c8817df70b2f7bd828dca62f6
			/*$('#stateNameId')
			.change(
					function() {

						var state = $(this).find(":selected")
						.val();

						$.ajax({
							type : "GET",
							url : projectPath+"loadDistrictByState",
							data : {
								"id" : state
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select District</option>';
								for (var i = 0; i < len; i++) {
									html += '<option value="'
										+ result[i]
									+ '">'
									+ result[i]
									+ '</option>';
								}
								html += '</option>';

								$("#districtId").prop(
										'disabled',
										false);
								$('#districtId').html(
										html);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});
*/
			/* Here is code for distrcit Selection for adding city */
			$('#stateDistrictId')
			.change(
					function() {

						var state = $(this).find(":selected")
						.val();

						$
						.ajax({
							type : "GET",
							url : projectPath+"loadDistrictByState",
							data : {
								"id" : state
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select District</option>';
								for (var i = 0; i < len; i++) {
									html += '<option value="'
										+ result[i]
									+ '">'
									+ result[i]
									+ '</option>';
								}
								html += '</option>';

								$("#districtCityId")
								.prop(
										'disabled',
										false);
								$('#districtCityId')
								.html(html);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			/*$('#districtId')
			.change(
					function() {

						var dist = $(this).find(":selected")
						.val();

						$
						.ajax({
							type : "GET",
							url : projectPath+"loadCityByDistrict",
							data : {
								"id" : dist
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select City</option>';
								for (var i = 0; i < len; i++) {
									html += '<option value="'
										+ result[i]
									+ '">'
									+ result[i]
									+ '</option>';
								}
								html += '</option>';

								$("#cityId").prop(
										'disabled',
										false);
								$('#cityId').html(html);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});*/

			/*
			 * Here is code for access topic according to category
			 * master trainer
			 */
/***************** changes made by om prakash *********************************************/
			
			$("#catMasterId").change(function() {

						var catgoryid = $(this).find(":selected").val();

						$.ajax({
							type : "GET",
							url : projectPath+"loadTopicByCategory",
							data : {
								"id" : catgoryid
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select Topic</option>';
	  	  			            $.each(result , function( key, value ) {
		  	  			        html += '<option value=' + key + '>'
		  			               + value
		  			               + '</option>';
		  	  			        })
	  	  			            html += '</option>';
								$("#lanMasterTrId").prop('disabled',false);
								$('#lanMasterTrId').html(html);

							},

							error : function(err) {
								console.log("not working. ERROR: "+ JSON.stringify(err));

							}

						});

					});

			$('#OutlineAcceptOrNeedTiImprovemenet').on(
					'hidden.bs.modal', function() {
						location.reload();
					});

			$('#approveContributorId').on('show.bs.tab', function() {
				location.reload();
			});



			$('#KeywordStatusAccept').on('hidden.bs.modal', function() {
				location.reload();
			});

			$('#videoViewIdAdmin').on('hidden.bs.modal', function() {
				location.reload();
			});

			$('#videoViewIdDomain').on('hidden.bs.modal', function() {
				location.reload();
			});

			$('#VideoStatusAccept').on('hidden.bs.modal', function() {
				location.reload();
			});

			$('#scriptStatusAcceptDomain').on('hidden.bs.modal',
					function() {
				location.reload();
			});

			$('#VideoStatusAccept').on('hidden.bs.modal', function() {
				location.reload();
			});

			$('.nav-item').on(
					'click',
					function() {
						localStorage.setItem('msg', "");
						$('.approve-success-msg').text(
								localStorage.getItem('msg'));
					});

			// Contributor

			// here is code for keyword review
			$('#keywordModale').on('hidden.bs.modal', function() {
				location.reload();

			});
			$('#scriptModale').on(
					'shown.bs.modal',
					function() {
						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();
						$.ajax({

							type : "GET",
							url : projectPath+"scriptPdf",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {
								var res =  filep + result;
								source = document
								.getElementById('viewScript');
								source.setAttribute('href', res);

								str = result[0].split("/");
								console.log(str);
								fileName = str[str.length - 1];
								console.log(str);
								$('#viewScript').html(fileName);
							},

							error : function(err) {
								console.log("not working. ERROR: "
										+ JSON.stringify(err));
							}
						});

					});

/********************** changes made by om prakash **************************************/
			
			$('#keywordModale').on('shown.bs.modal',function() {
						
						var tutorialID= $("#tutorialId").val();

						if(tutorialID !=0 ){
								$.ajax({
									type : "GET",
									url : projectPath+"viewKeyword",
									data : {
										"id" : tutorialID
									},
									contentType : "application/json",
									success : function(result) {
										if(result!=null){
											$("#keyword").val(result);
											$('#keyword').prop('readonly', true);
//											$("#keywordId").hide();
										}
										
		
									},
									error : function(err) {
										console.log("not working. ERROR: "+ JSON.stringify(err));
									}
		
								});
						
						}

					});

			// here is code for
			$('#outlineModalContri').on('hidden.bs.modal', function() {
				location.reload();

			});
			
	/********************* changes made by om prakash *******************************/
			
			$('#outlineModalContri').on('shown.bs.modal',function() {

						/*var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();*/
				
						var tutorialID= $("#tutorialId").val();
						
						if(tutorialID !=0 ){
							
								$.ajax({
		
									type : "GET",
									url : projectPath+"viewOutline",
									data : {
										"id" : tutorialID
									},
									contentType : "application/json",
									success : function(result) {
										if(result==null){
											$('#editOutline').hide();
										}
										editor.setData(result); // add
										// retrieved
										// outline
										// content
										// to editor
//										editor.isReadOnly = true;
		
									},
									error : function(err) {
										console.log("not working. ERROR: "+ JSON.stringify(err));
									}
		
								});
						
						}

					});

			// here is code for Script
			$('#scriptModale').on('hidden.bs.modal', function() {
				location.reload();

			});

			$('#slideModale').on('hidden.bs.modal', function() {
				location.reload();
			});

			/* load By Contributor user only contributor assign from */

//			$('#contributorId')
//			.click(
//			function() {
//			var userContributor = $(this).find(':selected').val();
//			$.ajax({
//			type : "GET",
//			url : "/loadLanguageByUser",
//			data : {
//			"id" : userContributor
//			},
//			contentType : "application/json",
//			success : function(result) {
//			var html = '';
//			var len = result.length;
//			html += '<option value="0">Select Language</option>';
//			for (var i = 0; i < len; i++) {
//			html += '<option value="'+ result[i]+ '">'+ result[i]+ '</option>';
//			}
//			html += '</option>';
//			$("#lanId").prop('disabled',false);
//			$('#lanId').html(html);
//			},
//			error : function(err) {
//			console.log("not working. ERROR: "+ JSON.stringify(err));
//			}
//			});
//			});
			/* here is code for download question */

			$('#inputLanguage1')
			.change(
					function() {

						var catgoryid = $(this).find(
								":selected").val();

						$
						.ajax({
							type : "GET",
							url : projectPath+"downloadQuestion",
							data : {
								"id" : catgoryid
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<a href="#">Click To Open</a>';
								for (var i = 0; i < len; i++) {

									html += '<a her="'
										+ result[i]
									+ '">'
									+ '<a href='
									+ result[i]
									+ '>'
									+ result[i]
									+ '</a>'
									+ '</option>';

								}
								html += '</option>';

								$("#input").prop(
										'disabled',
										false);

								$('#inputdiv').html(
										html);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));

							}

						});

					});

			/*
			 * master trainer depending on language wet topic
			 */

			$('#inputLanguage')
			.change(
					function() {

						var catgoryid = $(this).find(
								":selected").val();

						$
						.ajax({
							type : "GET",
							url : projectPath+"loadByLangugaeTopic",
							data : {
								"id" : catgoryid
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select Topic</option>';
								for (var i = 0; i < len; i++) {

									html += '<option value="'
										+ result[i]
									+ '">'
									+ result[i]
									+ '</option>';
								}
								html += '</option>';

								$("#selectLanguageId")
								.prop(
										'disabled',
										false);
								$('#selectLanguageId')
								.html(html);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			$('#slideModale').on(
					'shown.bs.modal',
					function() {

						var categoryid = $("#categoryId").val();

						var topicid = $("#topicId").val();

						var lanId = $("#lanId").val();

						$.ajax({

							type : "GET",

							url : projectPath+"sliedPdf",

							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},

							contentType : "application/json",

							success : function(result)

							{

								var res = filep+result;

								source = document
								.getElementById('sliedPdf');

								source.setAttribute('href', res);
								str = result[0].split("/");
								console.log(str);
								fileName = str[str.length - 1];
								console.log(str);
								$('#sliedPdf').html(fileName);
							},

							error : function(err) {

								console.log("not working. ERROR: "
										+ JSON.stringify(err));

							}

						});

					});

//			$('#lanId').change(
//					
//					function() {
//						alert('test');
//						var languageName = $("#option :selected").val();
//						$.ajax({
//							type : "GET",
//							url : projectPath+"loadCategoryByLanguage",
//							data : {
//								"id" : languageName
//							},
//							contentType : "application/json",
//							success : function(result) {
//								var html = '';
//								var len = result.length;
//								html += '<option value="0">Select Category</option>';
//								for (var i = 0; i < len; i++) {
//									html += '<option value="'+ result[i]+ '">'+ result[i]+ '</option>';
//								}
//								html += '</option>';
//								$("#catgoryByContributor").prop('disabled',false);
//								$('#catgoryByContributor').html(html);
//							},
//							error : function(err) {
//								console.log("not working. ERROR: "+ JSON.stringify(err));
//							}
//						});
//					});


//			question start
//			$('#fetch_questions').click(
//			function() {
//			alert('ques');
//			var categoryid = $('#catMasterId').val();
//			var topicid = $('#lanMasterTrId').val();
//			var lanId = $('#dwnByLanguageId').val();
//			$.ajax({
//			type : "GET",
//			url : "/displayQuestion",
//			data : {
//			"categorname" : categoryid,
//			"topicid" : topicid,
//			"lanId" : lanId
//			},
//			contentType : "application/json",
//			success : function(result) {
//			$('#questions').html(result);
//			console.log(result);
//			alert('success');
//			},
//			error : function(err) {
//			console.log("not working. ERROR: "+ JSON.stringify(err));
//			alert('error');
//			}
//			});
//			});
//			question end
			$('#fetch_questions').click(
			function() {
			$('#questionnaire').tab('show');
			
			});



			/*$('#catgoryByContributor').click(
					function() {
						// var languageName = $form.find( '#lanId'
						// ).val(),
						var category = $(this).find("option:selected").val();
						var languageName = $('#lanId').val();
						var userName = $('#contributorId').val();
						// var languageName=$("option :lanId").text();

						$.ajax({
							type : "GET",
							url : projectPath+"loadTopicByCategory",
							data : {
								"id" : category,
								"lanId" : languageName,
								"userName" : userName
							},
							contentType : "application/json",
							success : function(result) {
								var html = '';
								var len = result.length;
								for (var i = 0; i < len; i++) {
									html += '<option value="'+ result[i] + '">'+ result[i] + '</option>';
								}
								html += '</option>';
								$("#inputTopic").prop('disabled', false);
								$('#inputTopic').html(html);
							},
							error : function(err) {
								console.log("not working. ERROR: "
										+ JSON.stringify(err));
							}
						});

					});*/

			// here write code for keyword view //add content form
			$('#keywordModaleView').click(
					function() {

						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();

						$.ajax({
							type : "GET",
							url : projectPath+"viewKeyword",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {

								$("#keywordView").prop('disabled',
										false);
								$('#keywordView').html(result);

							},

							error : function(err) {
								console.log("not working. ERROR: "
										+ JSON.stringify(err));
							}

						});

					});

			// Here is Code for Quality Review keyword View

			$('#keywordModaleViewInQuality')
			.click(
					function() {

						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();

						$
						.ajax({
							type : "GET",
							url : projectPath+"viewKeywordInQuality",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {

								$(
										"#keywordViewInKeyword")
										.prop(
												'disabled',
												false);
								$(
								'#keywordViewInKeyword')
								.html(result);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			/* Here code to view keywored into Domain reviwer */

//			$('#keywordModaleViewInDomain').click(
//			function() {

//			var categoryid = $("#categoryId").val();
//			var topicid = $("#topicId").val();
//			var lanId = $("#lanId").val();

//			$.ajax({
//			type : "GET",
//			url : "/viewKeywordInDomain",
//			data : {
//			"categorname" : categoryid,
//			"topicid" : topicid,
//			"lanId" : lanId
//			},
//			contentType : "application/json",
//			success : function(result) {

//			$('#keywordViewInDomainKeyword').html(
//			result);

//			},

//			error : function(err) {
//			console.log("not working. ERROR: "
//			+ JSON.stringify(err));
//			}

//			});

//			});

			$('#videoViewId')
			.click(
					function() {

						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();

						$
						.ajax({

							type : "GET",
							url : projectPath+"viewVideo",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {

								// $("#VideoView").prop('disabled',false);
								// $('#VideoView').html(result);

								var res = result;

								source = document
								.getElementById('storedVideoId');
								source.setAttribute(
										'src', res);
								source.setAttribute(
										'type',
								'video/mp4')

								source.play(); // test
								// playback
								// of
								// new
								// video

								// $('#videoDiv').show()

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			// here is code for Quality View

			$('#videoViewId')
			.click(
					function() {

						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();
						s
						$
						.ajax({

							type : "GET",
							url : projectPath+"viewVideo",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {

								// $("#VideoView").prop('disabled',false);
								// $('#VideoView').html(result);

								var res = result;

								source = document
								.getElementById('storedVideoId');
								source.setAttribute(
										'src', res);
								source.setAttribute(
										'type',
								'video/mp4')

								source.play(); // test
								// playback
								// of
								// new
								// video

								// $('#videoDiv').show()

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			// here code outline View

			$('#outlineViewModel').click(
					function() {

						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();
						$.ajax({

							type : "GET",
							url : projectPath+"outlineView",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {

								$("#outlineViewResponse").prop(
										'disabled', false);
								$('#outlineViewResponse').html(result);

							},

							error : function(err) {
								console.log("not working. ERROR: "
										+ JSON.stringify(err));
							}

						});

					});

			// Script View Contributor

			$('#viewScriptId').click(
					function() {

						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();
						$.ajax({

							type : "GET",
							url : projectPath+"scriptPdf",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {
								var res = '[(@{/files/})]'+result;
								// alert("NEW video path : " + res);
								// //should be a valid path eg:
								// https://example.com/myvideo.mp4

								source = document
								.getElementById('ScriptPdf');
								source.setAttribute('href', res);

								// source.setAttribute('type','video/mp4')
								source.play();

							},

							error : function(err) {
								console.log("not working. ERROR: "
										+ JSON.stringify(err));
							}

						});

					});

			// Contributor Script modelView

			$('#slideModaleView').click(
					function() {

						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();
						$.ajax({

							type : "GET",
							url : projectPath+"sliedPdf",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {
								var res = result;
								// alert("NEW video path : " + res);
								// //should be a valid path eg:
								// https://example.com/myvideo.mp4

								source = document
								.getElementById('sliedPdf');
								source.setAttribute('href', res);
								// source.setAttribute('type','video/mp4')

								source.play();

							},

							error : function(err) {
								console.log("not working. ERROR: "
										+ JSON.stringify(err));
							}

						});

					});

			$('#videoModel').on('hidden.bs.modal', function() {
				location.reload();

			});

			$('#videoClick')
			.click(
					function() {
						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();

						$
						.ajax({

							type : "GET",
							url : projectPath+"viewVideo",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {

								var res = result;
								source = document
								.getElementById('storedVideoId');
								source.setAttribute(
										'src', res);
								source.setAttribute(
										'type',
								'video/mp4')

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			$('#VideoStatusAccept').on('hidden.bs.modal', function() {
				location.reload();

			});

			// Quality

			$('#OutlineAccepOrNeedToImprovementQuality').on(
					'hidden.bs.modal', function() {
						location.reload();

					});

			$('#scriptStatusAccept').on('hidden.bs.modal', function() {
				location.reload();

			});

			$('#slideAcceptOrNeedImp').on('hidden.bs.modal',
					function() {
				location.reload();

			});

			$('#VideoAccepOrNeedToImprovementQuality').on(
					'hidden.bs.modal', function() {
						location.reload();

					});

			$('#keywordAccepOrNeedToImprovementQuality').on(
					'hidden.bs.modal', function() {
						location.reload();

					});

			$('#categoryNameList').change(function() {

			});

			// Here is code for category display into category and
			// language

			$('#categoryId')
			.change(
					function() {
						var categoryid = $("#categoryNameList")
						.val();
						var languageid = $("#inputLanguageList")
						.val();

						$
						.ajax({

							type : "GET",
							url  : projectPath+"loadCategoryAndLanguage",
							data : {
								"id" : categoryid,
								"lanid" : language
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select language</option>';
								for (var i = 0; i < len; i++) {

									html += '<option value="'
										+ result[i]
									+ '">'
									+ result[i]
									+ '</option>';
								}
								html += '</option>';

								$("#inputLanguageList")
								.prop(
										'disabled',
										false);
								$('#inputLanguageList')
								.html(html);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});
			$( "#categoryname" ).change(function() {
				

				var catgoryid = $(this).val();
				
				$.ajax({
					type : "GET",
					url : projectPath+"listTopicsByCategory",
					data : {
						"id" : catgoryid
					},
					contentType : "application/json",
					success : function(result) {
						var html = '';
						var len = result.length;
						html += '<option value="0">Select Topic</option>';
						for (var i = 0; i < len; i++) {

							html += '<option value="'
								+ result[i]
							+ '">'
							+ result[i]
							+ '</option>';
						}
						html += '</option>';

//						$("#inputLanguage").prop('disabled',false);
//						$('#inputLanguage').html(html);
						$("#inputTopicName").prop('disabled',false);
						$('#inputTopicName').html(html);

					},
					error : function(err) {
						console.log("not working. ERROR: "+ JSON.stringify(err));
					}
				});
			});


			$('#inputTopicName').change(function() {
//				alert('here 3');
				var topic = $(this).val();
				var categoryName = $("#categoryname").val();
				$.ajax({
					type : "GET",
					url : projectPath+"listLangByCategoryTopic",
					data : {
						"category" : categoryName,
						"topic" : topic
					},
					contentType : "application/json",
					success : function(result) {
						console.log(result);

						var html = '';
						var len = result.length;
						html += '<option value="0">Select language</option>';
						for (var i = 0; i < len; i++) {

							html += '<option value="'
								+ result[i]
							+ '">'
							+ result[i]
							+ '</option>';
						}
						html += '</option>';

						$("#inputLanguage").prop('disabled',false);
						$('#inputLanguage').html(html);
					},
					error : function(err) {
						console.log("not working. ERROR: "+ JSON.stringify(err));
					}
				});

			});

			/* here we write code for calling Topic */

			$('#categoryId')
			.on('change',function() {
				var catgoryid = $(this).find(
				":selected").val();
				$
				.ajax({
					type : "GET",
					url : projectPath+"loadByCategoryTuturialTopic",
					data : {
						"id" : catgoryid
					},
					contentType : "application/json",
					success : function(result) {

						var html = '';
						var len = result.length;
						html += '<option value="0">Select Topic</option>';
						for (var i = 0; i < len; i++) {
							html += '<option value="'
								+ result[i]
							+ '">'
							+ result[i]
							+ '</option>';
						}
						html += '</option>';

						$("#inputTopic").prop(
								'disabled',
								false);
						$('#inputTopic').html(
								html);

					},

					error : function(err) {
						console
						.log("not working. ERROR: "
								+ JSON
								.stringify(err));
					}

				});

			});

			/* here we write code for calling language */

			$('#categoryId')
			.on('change',function() {
				var catgoryid = $(this).find(
				":selected").val();
				$
				.ajax({

					type : "GET",
					url : projectPath+"loadByCategoryTuturial",
					data : {
						"id" : catgoryid
					},
					contentType : "application/json",
					success : function(result) {

						var html = '';
						var len = result.length;
						html += '<option value="0">Select language</option>';
						for (var i = 0; i < len; i++) {
							html += '<option value="'
								+ result[i]
							+ '">'
							+ result[i]
							+ '</option>';
						}
						html += '</option>';

						$("#inputLanguage")
						.prop(
								'disabled',
								false);
						$('#inputLanguage')
						.html(html);

					},

					error : function(err) {
						console
						.log("not working. ERROR: "
								+ JSON
								.stringify(err));
					}

				});

			});

			// chages according to individual table by languge
			$('#preRequsite')
			.change(
					function() {
						var catgoryid = $(this).find(
						":selected").val();
						$
						.ajax({
							type : "GET",
							url : projectPath+"loadByCategoryTuturial",
							data : {
								"id" : catgoryid
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select Topic</option>';
								for (var i = 0; i < len; i++) {
									html += '<option value="'
										+ result[i]
									+ '">'
									+ result[i]
									+ '</option>';
								}
								html += '</option>';

								$("#inputLanguage1")
								.prop(
										'disabled',
										false);
								$('#inputLanguage1')
								.html(html);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			$('#inputLanguage1')
			.change(
					function() {

						var catgoryid = $(this).find(
								":selected").val();

						$
						.ajax({
							type : "GET",
							url : projectPath+"downloadQuestion",
							data : {
								"id" : catgoryid
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<a href="#">Click To Open</a>';
								for (var i = 0; i < len; i++) {

									html += '<a her="'
										+ result[i]
									+ '">'
									+ '<a href='
									+ result[i]
									+ '>'
									+ result[i]
									+ '</a>'
									+ '</option>';

								}
								html += '</option>';

								$("#input").prop(
										'disabled',
										false);

								$('#inputdiv').html(
										html);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));

							}

						});

					});

			/*
			 * master trainer depending on language wet topic
			 */

			$('#inputLanguage')
			.change(
					function() {

						var catgoryid = $(this).find(
								":selected").val();

						$
						.ajax({
							type : "GET",
							url : projectPath+"loadByLangugaeTopic",
							data : {
								"id" : catgoryid
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select Topic</option>';
								for (var i = 0; i < len; i++) {

									html += '<option value="'
										+ result[i]
									+ '">'
									+ result[i]
									+ '</option>';
								}
								html += '</option>';

								$("#selectLanguageId")
								.prop(
										'disabled',
										false);
								$('#selectLanguageId')
								.html(html);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			// chages according to individual table by languge

			$('#catId')
			.change(
					function() {

						var catgoryid = $(this).find(
								":selected").val();

						$
						.ajax({
							type : "GET",
							url : projectPath+"loadBycategorylanguage",
							data : {
								"id" : catgoryid
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select language</option>';
								for (var i = 0; i < len; i++) {

									html += '<option value="'
										+ result[i]
									+ '">'
									+ result[i]
									+ '</option>';
								}
								html += '</option>';

								$("#inputLan").prop(
										'disabled',
										false);
								$('#inputLan').html(
										html);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			/* Access topic according to langaueg */
/*********************************** Changes By Om Prakash **************************************************/
			$('#MasterCategoryId').change(function() {
						var categoryid = $(this).find(":selected").val();
						$.ajax({

							type : "GET",
							url : projectPath+"loadTopicByCategory",
							data : {
								"id" : categoryid
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
//								html += '<option value="0">Select any one topic</option>';
	  	  			            $.each(result , function( key, value ) {
		  	  			        html += '<option value=' + key + '>'
		  			               + value
		  			               + '</option>';
		  	  			        })
	  	  			            html += '</option>';
	  	  			            

								$("#inputTopic").prop('disabled',false);
								$('#inputTopic').html(html);

							},

							error : function(err) {
								console.log("not working. ERROR: "+ JSON.stringify(err));
							}

						});

					});
	/***************************************End ************************************************/

			/* load Topic by catgory contributor */

	/******************************* changes made by om prakash *************************************************/
			$('#categoryContributor').change(function() {
				
						var catgoryid = $('#categoryContributor').val();

						$.ajax({

							type : "GET",
							url : projectPath+"loadTopicByCategoryOnContributorRole",
							data : {
								"id" : catgoryid
							},
							contentType : "application/json",
							success : function(result) {

							//	html += '<option value="'+ result[i]+ '">'+ result[i]+ '</option>';

								var html = '';
								var len = result.length;
								html += '<option value="0">Select Topic</option>';
								$.each(result , function( key, value ) {
			  	  			        html += '<option value=' + key + '>'+ value  + '</option>';
			  	  			     })
								html += '</option>';

								$("#inputTopicContributor").prop('disabled',false);
								$('#inputTopicContributor').html(html);

							},

							error : function(err) {
								console.log("not working. ERROR: "	+ JSON.stringify(err));
							}

						});

					});

			/* contributor languages */

			$('#inputTopicContributor')	.change(function() {
				
				var catgoryid = $('#categoryContributor').val();
				var topicId=$(this).find(":selected").val();
				
						$.ajax({

							type : "GET",
							data : {
								"id" : catgoryid,
								"topicId":topicId
							},
							url : projectPath+"loadLanguageForContributorRoleTutorial",
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select Language</option>';
								for (var i = 0; i < len; i++) {
									html += '<option value="'+ result[i]+ '">'+ result[i]+ '</option>';
								}
								html += '</option>';

								$("#inputLanguageContributor").prop('disabled',false);
								$('#inputLanguageContributor').html(html);

							},

							error : function(err) {
								console.log("not working. ERROR: "+ JSON.stringify(err));
							}

						});

					});
			
			/********************************* end****************************************************/

			/*
			 * Access language depending on topic contributer
			 */

			$('#inputTopic')
			.change(
					function() {
//						alert('here');
						var catgoryid = $(this).find(
						":selected").val();
						$
						.ajax({
							type : "GET",
							url : projectPath+"loadlanguage",
							data : {
								"id" : catgoryid
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select any one language</option>';
								for (var i = 0; i < len; i++) {
									html += '<option value="'
										+ result[i]
									+ '">'
									+ result[i]
									+ '</option>';
								}
								html += '</option>';

								$("#inputLanguage")
								.prop(
										'disabled',
										false);
								$('#inputLanguage')
								.html(html);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));

							}

						});

					});
/******************************** changes made by om prakash ************************************/
			
			$('#outlineId').click(function() {
				
//						$(this).toggle();
						$('#editOutline').toggle();

						var saveInfo = editor.getData();
						var tutorialId=$("#tutorialId").val();
						
						console.log("******************");
						console.log(saveInfo);
						console.log("******************");
						/*var keywordArea = $("#keyword").val();*/
						var categoryid = $("#categoryId").val();
						var topicid = $("#topicID").val();
						var lanId = $("#lanId").val();
						localStorage.setItem("upload_outline_msg","Outline updated.");
//						editor.isReadOnly = true;

						$.ajax({
							type : "GET",
							url : projectPath+"addOutline",
							data : {
								"saveOutline" : saveInfo,
								"id" : tutorialId,
								"categoryname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
								
							},
							contentType : "application/json",
							success : function(result) {
								// $("#statusOutline").prop('disabled',
								// false);
								//var msg = 'Outline updated successfully.'
									// $('#statusOutline').html(html);
									//$('#statusOutlineC')
									//.addClass(
									//'d-block');
								//$('#statusOutlineC').html(msg);
								
								$.each(result , function( key, value ) {
			  	  			        if(key == 1){
										
										$('#statusOutlineC').addClass('d-block');
										$('#statusOutlineC').html(value);
										
									}else{
										$('#statusOutlineC').addClass('d-block');
								 		$('#statusOutlineC').addClass('alert-danger');

										$('#statusOutlineC').html(value);
									}
			  	  			     })

							},
							error : function(err) {
								console.log("not working. ERROR: "+ JSON.stringify(err));
								var msg = 'Error in updating outline.'
									// $('#statusOutline').html(html);
									$('#statusOutlineC').addClass('d-block');
								 $('#statusOutlineC').addClass('alert-danger');

								$('#statusOutlineC').html(msg);

							}

						});

					});

			$('#editOutline').click(function() {
				$(this).toggle();
				$('outlineId').toggle();

				editor.isReadOnly = false;
				// outlineId
				$('#outlineId').show();
				$("#outlineId").html("Save changes");

			});

//			$('#editKeyword').click(function() {
//				$(this).toggle();
//				$('keywordId').toggle();
//				$('#keyword').prop('readonly', false);
//				// outlineId
//				$('#keywordId').show();
//				$("#keywordId").html("Save changes");
//
//			});

			/* Save keyWord information into table */
	/*********************** changes made by om prakash *************************************/

			$('#keywordId').click(function() {
				
//						$(this).toggle();
//						$('#editKeyword').toggle();
						
						var keywordArea = $("#keywordArea").val();
						console.log("keywordArea "+keywordArea);
						var categoryid = $("#categoryId").val();
						console.log("categoryid "+categoryid);
						var topicid = $("#topicID").val();
						console.log("topicid "+topicid);
						var lanId = $("#lanId").val();
						console.log("lanId "+lanId);
//						var categoryid = $("#categoryId").val();
//						var topicid = $("#topicID").val();
//						var lanId = $("#lanId").val();
						var tutorialId=$("#tutorialId").val();
//						$('#keyword').prop('readonly', true);
						$.ajax({
							type : "GET",
							url : projectPath+"addKeyword",
							data : {
								"savekeyword" : keywordArea,
								"id" : tutorialId,
								"categoryname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {
								showStatus(SUCCESS,result);
//								$("#statuskeyword").prop('disabled',
//								false);
//								$('#statuskeyword').html(result);
								$.each(result , function( key, value ) {
			  	  			        if(key == 1){
										$('#status_kw').addClass('d-block');
										$('#status_kw').addClass('alert-success');
										$('#status_kw').html(value);
										
									}else{
										$('#status_kw').addClass('d-block');
								 		$('#status_kw').addClass('alert-danger');
										$('#status_kw').html(value);
									}
			  	  			     })

							},

							error : function(err) {
								console.log("not working. ERROR: "	+ JSON.stringify(err));
								var result = "Error"; showStatus(ERROR,result);
							}

						});

					});

			/*
			 * Here is code for save script
			 */

			$('#scriptId').click(function() {
						// here1
						var categoryid = $("#categoryId").val();
						var topicid = $("#topicID").val();
						var lanId = $("#lanId").val();
						var TutorialID=$("#tutorialId").val();

						var form = $('#upload-file-form-script')[0];
						var formData = new FormData(form);
//						alert(form);
						console.log(form);
						console.log(form[0]);

						formData.append('categoryname', categoryid);
						formData.append('topicid', topicid);
						formData.append('lanId', lanId);
						formData.append('id',TutorialID);

						$.ajax({
							type : "POST",
							url : projectPath+"addScript",
							data : formData,
							enctype : 'multipart/form-data',
							processData : false,
							contentType : false,
							cache : false,
							success : function(result) {
//								$("#statusofScript").prop('disabled',true);
//								$('#statusofScript').html(result[2]);
								$('#viewScript').html(result);
//								source = document.getElementById('storedVideoId');
//								source.setAttribute('src',result[1]);
								//var result = "Script updated successfully";
								//showStatus(SUCCESS,result);
								$('.upload-status').hide();
								$.each(result , function( key, value ) {
			  	  			        if(key == 1){
										$('#status_script').addClass('d-block');
										$('#status_script').html(value);
										
									}else{
										$('#status_script').addClass('d-block');
								 		$('#status_script').addClass('alert-danger');
										$('#status_script').html(value);
									}
			  	  			     })
								
							},

							error : function(err) {
								console.log("not working. ERROR: "+ JSON.stringify(err));
								var result = "Error";
								showStatus(ERROR,result);
							}
						});

					});

			/*
			 * here we write code for slide
			 */

			$('#slideId').click(function() {
						var categoryid = $("#categoryId").val();
						var topicid = $("#topicID").val();
						var lanId = $("#lanId").val();
						var TutorialID=$("#tutorialId").val();

						var form = $('#upload-file-form')[0];
						var formData = new FormData(form);

						formData.append('categoryname', categoryid);
						formData.append('topicid', topicid);
						formData.append('lanId', lanId);
						formData.append('id',TutorialID);
						

						console.log(formData);

						$.ajax({
							type : "POST",
							url : projectPath+"addSlide",
							data : formData,
							enctype : 'multipart/form-data',
							processData : false,
							contentType : false,
							cache : false,
							success : function(result) {
//								$("#statusofSlide").prop('disabled',true);
//								$('#statusofSlide').html(result[2]);

//								$('#sliedPdf').html(projectPath);
								/*$("#sliedPdf").prop('href', result[1]);*/
								//var result = "Slide uploaded successfully";
								//showStatus(SUCCESS,result);
								$.each(result , function( key, value ) {
			  	  			        if(key == 1){
										$('#status_slide').addClass('d-block');
										$('#status_slide').addClass('alert-success');
										$('#status_slide').html(value);
										
									}else{
										$('#status_slide').addClass('d-block');
								 		$('#status_slide').addClass('alert-danger');
										$('#status_slide').html(value);
									}
			  	  			     })

							},

							error : function(err) {
								alert('error');
								console.log("not working. ERROR: "+ JSON.stringify(err));
							var result = "Slide uploaded successfully";
							showStatus(SUCCESS,result);
							}

						});

					});





			/*		Here is code for graphics*/

			// here is code for
			$('#grahicsModale').on('hidden.bs.modal', function() {
				location.reload();

			});

			


			/* video for thumnail and video */
/******************** changes made by om prakash *****************************************/
			
			$('#videoId').click(function() {
//						alert('videoId');
						var categoryid = $("#categoryId").val();
						var topicid = $("#topicID").val();
						var lanId = $("#lanId").val();
						var tutorialID=$('#tutorialId').val();

						var form = $('#upload-file-form-video')[0];
						var formData = new FormData(form);
						
						formData.append('id',tutorialID);
						formData.append('categoryname',categoryid);
						formData.append('topicid', topicid);
						formData.append('lanId', lanId);

						$
						.ajax({
							type : "POST",
							url : projectPath+"addVideo",
							data : formData,
							enctype : 'multipart/form-data',
							processData : false,
							contentType : false,
							cache : false,
							success : function(result) {

								$("#statusofVideo").prop('disabled',true);
//								$('#statusofVideo').html(result);
								//showStatus(SUCCESS,result);

							/*	source = document.getElementById('storedVideoId');
								source.setAttribute('src',result[1]);
								source.setAttribute('type','video/mp4')*/
								$.each(result , function( key, value ) {
			  	  			        if(key == 1){
										$('#status_video').addClass('d-block');
										$('#status_video').addClass('alert-success');
										$('#status_video').html(value);
										
									}else{
										$('#status_video').addClass('d-block');
								 		$('#status_video').addClass('alert-danger');
										$('#status_video').html(value);
									}
			  	  			     })

							},

							error : function(err) {
								console
								.log("not working. ERROR: "+ JSON.stringify(err));
							}

						});

					});

			$('#PrerequisiteVideoId')
			.click(
					function() {

						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();

						var form = $('#upload-file-form-prerequisite')[0];
						var formData = new FormData(form);

						formData.append('categoryid',
								categoryid);
						formData.append('topicid', topicid);
						formData.append('lanId', lanId);

						$
						.ajax({
							type : "POST",
							url : projectPath+"prerequisite",
							data : formData,
							enctype : 'multipart/form-data',
							processData : false,
							contentType : false,
							cache : false,
							success : function(result) {

								$(
										"#statusofprerequisite")
										.prop(
												'disabled',
												true);
								$(
								'#statusofprerequisite')
								.html(result);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			$('#myCheck').click(
					function() {

						var checkbox = $("#myCheck").val();

						$.ajax({

							type : "GET",
							url : projectPath+"keyword",
							data : {
								"id" : keywordArea
							},
							contentType : "application/json",
							success : function(result) {
								$("#statuskeyword").prop('disabled',
										false);
								$('#statuskeyword').html(result);

							},

							error : function(err) {
								console.log("not working. ERROR: "
										+ JSON.stringify(err));
							}

						});

					});

			/* here calling approve button for contributorvideoUpload */
/***************************** changes made by om prakash **************************************************/
			
			$('.approveContributor').click(
					function() {
						// use localStorage to retrieve information on
						// page refresh
						localStorage.setItem('activeTab', "Contributer");

						var contributionId = $(this).val();

						$.ajax({
							type : "GET",
							url : projectPath+"enableRoleById",
							data : {
								"id" : contributionId
							},
							contentType : "application/json",
							success : function(result) {

								$("#statusContributor").prop('disabled', false);

								localStorage.setItem('msg', result);
								location.reload();

							},

							error : function(err) {
								console.lo3g("not working. ERROR: "+ JSON.stringify(err));
							}

						});

					});

			// Here is code for Admin Reviwer Approve
/***************************** changes made by om prakash **************************************************/
			
			$('.approveAdmin').click(function() {
						// use localStorage to retrieve information on
						// page refresh
						localStorage.setItem('activeTab', "Admin");
						var contributionId = $(this).val();

						$.ajax({
							type : "GET",
							url : projectPath+"enableRoleById",
							data : {
								"id" : contributionId
							},
							contentType : "application/json",
							success : function(result) {

								$("#statusAdmin").prop('disabled',false);
								localStorage.setItem('msg', result);

								$('#ContributerPage').on('hidden.bs.modal', function() {

											location.reload();
								});

								location.reload();
							},

							error : function(err) {
								console.lo3g("not working. ERROR: "+ JSON.stringify(err));
							}

						});

					});

			// Here is code for Quality Reviweer
/***************************** changes made by om prakash **************************************************/
			
			$('.approveQuality').click(function() {
						// use localStorage to retrieve information on
						// page refresh
						localStorage.setItem('activeTab', "Quality");
						var contributionId = $(this).val();

						$.ajax({
							type : "GET",
							url : projectPath+"enableRoleById",
							data : {
								"id" : contributionId
							},
							contentType : "application/json",
							success : function(result) {

								$("#statusQuality").prop('disabled',false);
								$('#statusQuality').html(result);
								localStorage.setItem('msg', result);

								$('#ContributerPage').on('hidden.bs.modal', function() {

											location.reload();

								});

								location.reload();

							},

							error : function(err) {
								console.lo3g("not working. ERROR: "
										+ JSON.stringify(err));
							}

						});

					});

			// Here is code for Master Trainer

			$('.approvemaster').click(function() {
						// use localStorage to retrieve information on
						// page refresh
						localStorage.setItem('activeTab',"MasterTrainer");

						var contributionId = $(this).val();

						$.ajax({
							type : "GET",
							url : projectPath+"enableRoleById",
							data : {
								"id" : contributionId
							},
							contentType : "application/json",
							success : function(result) {

								$("#statusMaster").prop('disabled',false);
								$('#statusMaster').html(result);
								localStorage.setItem('msg', result);

								$('#ContributerPage').on('hidden.bs.modal', function() {

											location.reload();

								});

								location.reload();

							},

							error : function(err) {
								console.lo3g("not working. ERROR: "+ JSON.stringify(err));
							}

						});

					});
			
			//Following function refreshes the table dynamically whenever a row is deleted
			$('.dynamicTable tbody').on( 'click', '.rejectRole', function () {
				//alert('a');
				var elem = $(this).parents('table');
				var id = $(elem).attr('id');
				var table = $('#'+id).DataTable();	
		    	table.row( $(this).parents('tr') ).remove().draw();
			} );
			
			//Following function refreshes the table dynamically whenever a row is deleted
			$('.dynamicTable tbody').on( 'click', '.approveRole', function () {
				//alert('b');
				var elem = $(this).parents('table');
				var id = $(elem).attr('id');
				var table = $('#'+id).DataTable();	
		    	table.row( $(this).parents('tr') ).remove().draw();
			} );
			
			$('.rejectRole').click(function() {
				// use localStorage to retrieve information on
				// page refresh
				//alert('Here');
				localStorage.setItem('activeTab',"MasterTrainer");
				var contributionId = $(this).val();
				
				
				var table = $('#tableIdContributor').DataTable();

				$.ajax({
					type : "GET",
					url : projectPath+"deleteRole",
					data : {
						"id" : contributionId
					},
					contentType : "application/json",
					success : function(result) {

						$("#statusMaster").prop('disabled',false);
						$('#statusMaster').html(result);
						localStorage.setItem('msg', result);
						table.draw();
					/*	$('#ContributerPage').on('hidden.bs.modal', function() {

									location.reload();

						});

						location.reload();*/

					},

					error : function(err) {
						console.lo3g("not working. ERROR: "+ JSON.stringify(err));
					}

				});

			});
			
/**************************************** End ************************************************************/

			// here is code for approve DomainReviwer By admin

			$('.approveDomain').click(
					function() {
						// use localStorage to retrieve information on
						// page refresh
						localStorage.setItem('activeTab', "Domain");

						var contributionId = $(this).val();

						$.ajax({
							type : "GET",
							url : projectPath+"addDomainRoleById",
							data : {
								"id" : contributionId
							},
							contentType : "application/json",
							success : function(result) {

								$("#statusDomain").prop('disabled',
										false);
								$('#statusDomain').html(result);
								localStorage.setItem('msg', result);

								$('#ContributerPage').on(
										'hidden.bs.modal', function() {

											location.reload();

										});

								location.reload();

							},

							error : function(err) {
								console.lo3g("not working. ERROR: "
										+ JSON.stringify(err));
							}

						});

					});

			$('#rejectContributionId').click(
					function() {

						var contributionId = $("#contributorId").val();
//						alert(contributionId);

						$.ajax({
							type : "GET",
							url : projectPath+"rejectContributorById",
							data : {
								"id" : contributionId
							},
							contentType : "application/json",
							success : function(result) {

								$("#statusContributor").prop(
										'disabled', false);
								$('#statusContributor').html(result);

							},

							error : function(err) {
								console.log("not working. ERROR: "
										+ JSON.stringify(err));
							}

						});

					});

			/* load By Contributor user only contributor assign from */

/************************* changes made by om prakash **********************************************/
			
			$('#contributorId').on('change',function() {
						
						var userContributor = $(this).find(':selected').val();
						console.log(userContributor);
						$.ajax({
							type : "GET",
							url : projectPath+"loadLanguageByUser",
							data : {
								"id" : userContributor
							},
							contentType : "application/json",
							success : function(result) {

								console.log(result);
								var html = '';
								var len = result.length;
								html += '<option value="0">Select Language</option>';
								for (var i = 0; i < len; i++) {
									html += '<option value="'+ result[i]+ '">'+ result[i]+ '</option>';
								}
								html += '</option>';

								$("#lanId").prop('disabled',false);
								$('#lanId').html(html);

							},

							error : function(err) {
								console.log("not working. ERROR: "+ JSON.stringify(err));
							}
						});

					});
			
/************************* changes made by om prakash **********************************************/			
			$('#lanId').on('change',function() {
				console.log('test');
					var languageName = $("#option :selected").text();

						$.ajax({
							type : "GET",
							url : projectPath+"loadCategory",
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;
								html += '<option value="0">Select Category</option>';
								$.each(result , function( key, value ) {
				  	  			        html += '<option value=' + key + '>'
				  			               + value
				  			               + '</option>';
				  	  			 })
			  	  			    html += '</option>';
								
							/*	for (var i = 0; i < len; i++) {
									html += '<option value="'+ result[i]+ '">'+ result[i]+ '</option>';
								}
								html += '</option>';*/

								$("#catgoryByContributor").prop('disabled',false);
								$('#catgoryByContributor').html(html);

							},

							error : function(err) {
								console.log("not working. ERROR: "+ JSON.stringify(err));
							}
						});

					});
			
/************************* changes made by om prakash **********************************************/	
			$('#catgoryByContributor').on('change',function() {
						var category = $(this).find("option:selected").val();
				
						$.ajax({
							type : "GET",
							url : projectPath+"loadTopicByCategory",
							data : {
								"id" : category
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;

								$.each(result , function( key, value ) {
			  	  			        html += '<option value=' + key + '>'
			  			               + value
			  			               + '</option>';
			  	  			 		})
			  	  			 	html += '</option>';
								
								/*for (var i = 0; i < len; i++) {
									html += '<option value="'
										+ result[i] + '">'
										+ result[i] + '</option>';
								}
								html += '</option>';*/
							
								$("#assignTopic").prop('disabled', false);
								$('#assignTopic').html(html);

							},
							error : function(err) {
								console.log("not working. ERROR: "+ JSON.stringify(err));
							}
						});

					});
			
/*********************************** end*******************************************************/

			// here write code for keyword view //add content form

			$('#keywordModaleView').click(
					function() {

						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();

						$.ajax({
							type : "GET",
							url : projectPath+"viewKeyword",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {

								$("#keywordView").prop('disabled',false);
								$('#keywordView').html(result);

							},

							error : function(err) {
								console.log("not working. ERROR: "
										+ JSON.stringify(err));
							}

						});

					});

			// Here is Code for Quality Review keyword View

			$('#keywordModaleViewInQuality')
			.click(
					function() {

						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();

						$
						.ajax({
							type : "GET",
							url : projectPath+"viewKeywordInQuality",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {

								$(
										"#keywordViewInKeyword")
										.prop(
												'disabled',
												false);
								$(
								'#keywordViewInKeyword')
								.html(result);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			/* Here code to view keywored into Domain reviwer */

			$('#keywordModaleViewInDomain').click(
					function() {

						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();

						$.ajax({
							type : "GET",
							url : projectPath+"viewKeywordInDomain",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {

								$('#keywordViewInDomainKeyword').html(
										result);

							},

							error : function(err) {
								console.log("not working. ERROR: "
										+ JSON.stringify(err));
							}

						});

					});

			$('#videoViewId')
			.click(
					function() {
						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();

						$
						.ajax({

							type : "GET",
							url : projectPath+"viewVideo",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {
								var res = result;

								source = document
								.getElementById('storedVideoId');
								source.setAttribute(
										'src', res);
								source.setAttribute(
										'type',
								'video/mp4')
							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			// here is code for Quality View

			$('#videoViewId')
			.click(
					function() {
						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();

						$
						.ajax({

							type : "GET",
							url : projectPath+"viewVideo",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {
								var res = result;

								source = document
								.getElementById('storedVideoId');
								source.setAttribute(
										'src', res);
								source.setAttribute(
										'type',
								'video/mp4')
							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			// Script View Contributor

			$('#viewScript')
			.click(
					function() {

						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();
						$
						.ajax({

							type : "GET",
							url : projectPath+"scriptPdf",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {
								var res = '[(@{/files/})]'+result;
								source = document
								.getElementById('viewScript');
								source.setAttribute(
										'href', res);

								var win = window.open(
										res, '_blank');
								if (win) {
									// Browser has
									// allowed it to be
									// opened
									win.focus();
								} else {
									// Browser has
									// blocked it
//									alert('Please allow popups for this website');
								}
							},

							error : function(err) {
//								alert('failed');
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			// Contributor Script modelView

			$('#slideModaleView').click(
					function() {

						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();
						$.ajax({

							type : "GET",
							url : projectPath+"sliedPdf",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {
								var res = result;
								source = document
								.getElementById('sliedPdf');
								source.setAttribute('href', res);
							},

							error : function(err) {
								console.log("not working. ERROR: "
										+ JSON.stringify(err));
							}

						});

					});

			// here is code for keyword accept or need to improvement
//			$('#AcceptOrNeedToImprovemenetByDomain').change(
			$('#AcceptOrNeedToImprovemenetByDomain').click(function() {
						
						var tutorialId = $("#tutorialId").val();

						var vals = $("#KeywordAcceptDomain").val();

						if (vals === '0') {

//							$('#keywordNeedImprovement').css({
//							'visibility' : 'hidden'
//							});

							alert("Select Accept Or Need To Improvement");

						} else if (vals === '1') {

//							$('#keywordNeedImprovement').css({
//							'visibility' : 'hidden'
//							});

//							alert("Hello");

							$.ajax({
								type : "GET",
								url : projectPath+"acceptDomainKeywords",
								data : {
									"id":tutorialId
								},
								contentType : "application/json",
								success : function(
										result) {
									showStatus(SUCCESS,result);
//									$("#statusKeywordByDomain").prop('disabled',false);
//									$('#statusKeywordByDomain').html(result);

								},

								error : function(err) {
									console.lo3g("not working. ERROR: "+ JSON.stringify(err));
									result = "Error";
									showStatus(ERROR,result);
								}

							});

						} else if (vals === '2') {

							$('#keywordNeedImprovement').css({
								'visibility' : 'visible'
							});

							var msg = $("#keywordCommentMsg").val();

							$.ajax({
								type : "GET",
								url : projectPath+"commentByReviewer",
								data : {
									"id" : tutorialId,
									"msg" : msg,
									"type" : "Keyword",
									"role" : "Domain"
								},
								contentType : "application/json",

								success : function(
										result) {

//									$("#statusKeywordByDomain").prop('disabled',false);
//									$('#statusKeywordByDomain').html(result);
									showStatus(SUCCESS,result);
								},

								error : function(err) {
									console.log("not working. ERROR: "+ JSON.stringify(err));
									result = "Error";
									showStatus(ERROR,result);
								}

							});
						}

					});

			// Display comment box for need to improvemnet on outline

			// $('#outlineAcceptOtrNeedImp').click(function() {
			$(".select-review-feedback").change(function() {
				var val = $(this).val();
				if (val == '2') {
					$('.textarea-comment').show();
				} else {
					$('.textarea-comment').hide();
				}

				// var vals = $("#outlineAcceptOtrNeedImp").val();
				//
				// if (vals === '0') {
				//
				// $('#OutlineNeedImp').css({
				// 'visibility' : 'hidden'
				// });
				//
				// } else if (vals === '1') {
				//
				// $('#OutlineNeedImp').css({
				// 'visibility' : 'hidden'
				// });
				//
				// } else if (vals === '2') {
				//
				// $('#OutlineNeedImp').css({
				// 'visibility' : 'visible'
				// });
				// }

			});

/******************* changes made by om prakash **************************************/
			
			$('#outlineAcceptOrNeedToImpClick').click(function() {

						var tutorialId = $("#tutorialId").val();

						var vals = $("#outlineAcceptOtrNeedImp")
						.val();

						if (vals === '0') {

							// $('#OutlineNeedImp').css({
							// 'visibility' : 'hidden'
							// });

							alert("Please Select Accept Or Need To Improvement");

						} else if (vals === '1') {

							// $('#OutlineNeedImp').css({
							// 'visibility' : 'hidden'
							// });

							$.ajax({
								type : "GET",
								url : projectPath+"acceptDomainOutline",
								data : {
									"id" : tutorialId
								},
								contentType : "application/json",
								success : function(result) {
									// $("#statusOutlineByDomain").prop('disabled',false);
									// $('#statusOutlineByDomain').html(result);
									showStatus(SUCCESS,result);
								},

								error : function(err) {
									console
									.log("not working. ERROR: "+ JSON.stringify(err));
									var result = 'Error occured while submitting outline review feedback.';
									showStatus(ERROR,result);
								}
							});

						} else if (vals === '2') {
							// $('#OutlineNeedImp').css({
							// 'visibility' : 'visible'
							// });
							var msg = $("#msgCommentOutline").val();
							$.ajax({
								type : "GET",
								url : projectPath+"commentByReviewer",
								data : {
									"id" : tutorialId,
									"msg" : msg,
									"type" : "Outline",
									"role" : "Domain"
								},
								contentType : "application/json",
								success : function(
										result) {
									showStatus(SUCCESS,result);
									// $("#statusOutlineByDomain").prop('disabled',false);
									// $('#statusOutlineByDomain').html(result);
								},

								error : function(err) {
									console.log("not working. ERROR: "+ JSON.stringify(err));
									result = 'Error occured while submitting outline review feedback.';
									showStatus(ERROR,result);
								}
							});
						}

					});

			// here is code for Script for domain

//			$('#scriptAccept').click(function() {
			$('#scriptAccept').change(function() {

				var vals = $("#scriptAccept").val();

				if (vals === '2') {
					$('#scriptNeedImprovement').show();
				}else{
					$('#scriptNeedImprovement').hide();
				}

//				if (vals === '0') {
//				$('#scriptNeedImprovement').css({
//				'visibility' : 'hidden'
//				});
//				} else if (vals === '1') {

//				$('#scriptNeedImprovement').css({
//				'visibility' : 'hidden'
//				});

//				} else if (vals === '2') {

//				$('#scriptNeedImprovement').css({
//				'visibility' : 'visible'
//				});

//				}

			});

			$('#VideoAccept').click(function() {

				var vals = $(this).find(":selected").val();

				if (vals === '0') {
					$('#videoNeedImprovement').css({
						'visibility' : 'hidden'
					});

				} else if (vals === '1') {

					$('#videoNeedImprovement').css({
						'visibility' : 'visible'
					});

				}

			});

//			$('#VideoAcceptAdmin').click(function() {
			$('#VideoAcceptAdmin').change(function() {

				var vals = $('#VideoAcceptAdmin').val();

				if (vals === '2') {
					$('#videoNeedImprovement').show();
				}else{
					$('#videoNeedImprovement').hide();
				}
//				if (vals === '0') {
//				$('#videoNeedImprovement').css({
//				'visibility' : 'hidden'
//				});

//				} else if (vals === '1') {

//				$('#videoNeedImprovement').css({
//				'visibility' : 'hidden'
//				});

//				l

//				} else if (vals === '2') {

//				$('#videoNeedImprovement').css({
//				'visibility' : 'visible'
//				});

//				}

			});

			// Display domain Outline

			$('#outlineViewModelDomain').click(
					function() {

						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();
						$.ajax({

							type : "GET",
							url : projectPath+"outlineViewDomain",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {

								$('#outlineViewResponseDomain').val(
										result)

							},

							error : function(err) {
								console.log("not working. ERROR: "
										+ JSON.stringify(err));
							}

						});

					});

			/* View Script from domain */

			$('#viewScriptIdDomain')
			.click(
					function() {

						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();

						$
						.ajax({
							type : "get",
							url : projectPath+"scriptPdfDomain",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {
								var res = result;

								source = document
								.getElementById('scriptPdfDomain');
								source.setAttribute(
										'href', res);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			/* view Video By Domain */

			$('#videoViewIdDomain')
			.click(
					function() {

						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();

						$
						.ajax({

							type : "GET",
							url : projectPath+"viewVideoDomain",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {

								var res = result;

								source = document
								.getElementById('storedVideoIdDomain');
								source.setAttribute(
										'src',res);
								source.setAttribute(
										'type',
								'video/mp4')

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			/* Here is code for comment on componenet outline */

			$('#submitCommentId')
			.click(
					function() {

						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();
						var commentOutlineMsg = $(
						"#msgCommentOutline").val();

						$
						.ajax({

							type : "GET",
							url : projectPath+"commentOnOutline",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId,
								"commentOutlineMsg" : commentOutlineMsg,
							},
							contentType : "application/json",
							success : function(result) {
								$("#saveComment").prop(
										'disabled',
										false);
								$('#saveComment').html(
										result);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			/* Here is code for comment on componenet Video Domain */

			$('#videoAcceptOrNeedToImprovemenet').click(
					function() {

						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();
						var videoCommentMsg = $("#videoCommentMsg")
						.val();

						$.ajax({

							type : "GET",
							url : projectPath+"commentOnVideo",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId,
								"videoCommentMsg" : videoCommentMsg
							},
							contentType : "application/json",
							success : function(result) {

								$("#saveCommentVideo").prop('disabled',
										false);
								$('#saveCommentVideo').html(result);

							},

							error : function(err) {
								console.log("not working. ERROR: "
										+ JSON.stringify(err));
							}

						});

					});

			/* Here is code for admin Video */

			$('#videoViewAdmin')
			.click(
					function() {
						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();

						$
						.ajax({

							type : "GET",
							url : projectPath+"viewVideoAdmin",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {

								var res = result;

								source = document
								.getElementById('storedVideoId');
								source.setAttribute(
										'src',res);
								source.setAttribute(
										'type',
								'video/mp4')
								source.play();

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			$('#videoViewIdAdmin')
			.click(
					function() {
						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();

						$
						.ajax({

							type : "GET",
							url : projectPath+"viewVideoAdmin",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {

								var res = result;

								source = document
								.getElementById('storedVideoId');
								source.setAttribute(
										'src',
										res);
								source.setAttribute(
										'type',
								'video/mp4')

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			/* here is code for video accepet or need to improvement */
/************************ changes made by om prakash ***************************************/
			
			$('#videoAcceptOrNeedToImprovemenetByAdmin').click(function() {

						
						var tutorialId = $("#tutorialId").val();

						var vals = $("#VideoAcceptAdmin").val();

						if (vals == 0) {
							$('#msgVideoCommentByAdmin').css({
								'visibility' : 'hidden'
							});

							alert("Select Accept Or Need To Improvement");

						} else if (vals == 1) {

							$('#msgVideoCommentByAdmin').css({
								'visibility' : 'hidden'
							});

							$
							.ajax({

								type : "GET",
								url : projectPath+"acceptAdminVideo",
								data : {
									"id" : tutorialId,
									"show_status" :1
								},
								contentType : "application/json",
								success : function(result) {

									$("#statusVideoByAdmin").prop('disabled',false);
									$('#statusVideoByAdmin').html(result);
										$('#success_msg').html(result);
									showStatus(SUCCESS, result);

								},

								error : function(err) {
									console.log("not working. ERROR: "+ JSON.stringify(err));
									result = "Error";
									showStatus(ERROR, result);
								}

							});

						} else if (vals == 2) {

							$('#msgVideoCommentByAdmin').css({
								'visibility' : 'visible'
							});

							var msg = $("#msgVideoCommentByAdmin").val();

//							alert("mse admin player" + msg);

							$.ajax({

								type : "GET",
								url : projectPath+"commentByAdminReviewer",
								data : {
									"id" :tutorialId,
									"msg" : msg
								},
								contentType : "application/json",
								success : function(result) {

									$("#statusVideoByAdmin").prop('disabled',false);
									$('#statusVideoByAdmin').html(result);

									showStatus(SUCCESS, result);

								},

								error : function(err) {
									console.log("not working. ERROR: "+ JSON.stringify(err));
									result = "Error";
									showStatus(ERROR, result);
								}

							});

						}

					});
			
		/*************************** end ***********************************/

			/*
			 * here is code for domain review Video Acdept or Need To
			 * improvemenet
			 */

//			$('#VideoAcceptDomain').click(function() {
			$('#VideoAcceptDomain').change(function() {

				var vals = $("#VideoAcceptDomain").val();

				if (vals === '2') {
					$('#videoNeedImprovement').show();
				}else{
					$('#videoNeedImprovement').hide();
				}
//				if (vals === '0') {
//				$('#videoNeedImprovement').css({
//				'visibility' : 'hidden'
//				});
//				} else if (vals === '1') {
//				$('#videoNeedImprovement').css({
//				'visibility' : 'hidden'
//				});

//				} else if (vals === '2') {

//				$('#videoNeedImprovement').css({
//				'visibility' : 'visible'
//				});

//				}

			});

			/* Here is code keywod accept or need to improvement */
//			$('#KeywordAcceptDomain').click(function() {
			$('#KeywordAcceptDomain').change(function() {
//				alert('HERE');
				var vals = $("#KeywordAcceptDomain").val();
				if (vals === '2'){
					$('#keywordNeedImprovement').show();
				}

//				if (vals === '0') {
//				$('#keywordNeedImprovement').css({
//				'visibility' : 'hidden'
//				});
//				} else if (vals === '1') {
//				$('#keywordNeedImprovement').css({
//				'visibility' : 'hidden'
//				});

//				} else if (vals === '2') {

//				$('#keywordNeedImprovement').css({
//				'visibility' : 'visible'
//				});

//				}

			});

			/* end */

			/* here is code for video accepet or need to improvement */
/************************ changes made by om prakash **********************************/
			
			$('#videoAcceptOrNeedToImprovemenetByDomain').click(function() {

						
						var tutorialId = $("#tutorialId").val();

						var vals = $("#VideoAcceptDomain")
						.val();

						if (vals == '0') {
							alert("Select Accept Or Need To Improvement");

						} else if (vals == '1') {

							$
							.ajax({

								type : "GET",
								url : projectPath+"acceptDomainVideo",
								data : {
									"id" : tutorialId
								},
								contentType : "application/json",
								success : function(
										result) {
									showStatus(SUCCESS, result);
//									$("#statusVideoByDomain").prop('disabled',false);
//									$('#statusVideoByDomain').html(result);

								},

								error : function(err) {
									console.log("not working. ERROR: "+ JSON.stringify(err));
									result = "Error";
									showStatus(ERROR, result);
								}
							});

						} else if (vals == '2') {
							
							var msg = $("#videoCommentMsg").val();

							$.ajax({

								type : "GET",
								url : projectPath+"commentByReviewer",
								data : {
									"id" :tutorialId,
									"msg" : msg,
									"type" : "Video",
									"role" : "Domain"
								},
								contentType : "application/json",
								success : function(
										result) {
//									$("#statusVideoByDomain").prop('disabled',false);
//									$('#statusVideoByDomain').html(result);
									showStatus(SUCCESS, result);

								},

								error : function(err) {
									console.log("not working. ERROR: "+ JSON.stringify(err));
									result = "Error";
									showStatus(ERROR, result);
								}

							});

						}

					});

			// Here is code for Domain review Accept or Need To
			// Improvement
/****************** changes made by om prakaSH **********************************/
			
			$('#scriptAcceptOrNeedToImprovemenetDomain').click(function() {

						
						var tutorialId = $("#tutorialId").val();

						var vals = $("#scriptAcceptDomain")
						.val();

						if (vals == '0') {
							alert("Select Accept Or Need To Improvement");

						} else if (vals == '1') {

							$
							.ajax({

								type : "GET",
								url : projectPath+"acceptDomainScript",
								data : {
									"id" : tutorialId
								},
								contentType : "application/json",
								success : function(result) {
//									$("#saveCommentScript").prop('disabled',false);
//									$('#saveCommentScript').html(result);
									showStatus(SUCCESS,result);
								},

								error : function(err) {
									console.log("not working. ERROR: "+ JSON.stringify(err));
									result = "Error";
									showStatus(ERROR,result);
								}
							});

						} else if (vals == "2") {

							var msgScript = $("#msgScriptDomain").val();
							
							$.ajax({
								type : "GET",
								url : projectPath+"commentByReviewer",
								data : {
									"id" : tutorialId,
									"msg" : msgScript,
									"type" : "Script",
									"role" : "Domain"
								},
								contentType : "application/json",
								success : function(result) {

									$("#saveCommentScript").prop('disabled',false);
									$('#saveCommentScript').html(result);
									showStatus(SUCCESS,result);
								},

								error : function(err) {
									console.log("not working. ERROR: "+JSON.stringify(err));
									result = "Error";
									showStatus(ERROR,result);
								}

							});

						}

					});

			/*
			 * Here is code for comment on componenet script Quality
			 * accept or need To Improvement
			 */
/****************** changes made by om prakash *********************************/
			
			$('#scriptAcceptOrNeedToImprovemenet').click(function() {

				var tutorialId = $("#tutorialId").val();

						var vals = $("#scriptAccept").val();

						if (vals == '0') {
							alert("Select Accept Or Need To Improvement");

						} else if (vals == '1') {

							$
							.ajax({

								type : "GET",
								url : projectPath+"acceptQualityScript",
								data : {
									"id" : tutorialId,
				
								},
								contentType : "application/json",
								success : function(
										result) {
//									$("#saveCommentScript").prop('disabled',false);
//									$('#saveCommentScript').html(result);
									showStatus(SUCCESS,result);

								},

								error : function(err) {
									console.log("not working. ERROR: "+ JSON.stringify(err));
									result = "Error";
									showStatus(ERROR,result);
								}

							});

						} else if (vals == "2") {

							var msgScript = $("#msgScriptQuality").val();

							$
							.ajax({

								type : "GET",
								url : projectPath+"commentByReviewer",
								data : {
									"id" : tutorialId,
									"msg" : msgScript,
									"type" : "Script",
									"role" : "Quality"
								},
								contentType : "application/json",
								success : function(
										result) {
//									$("#saveCommentScript").prop('disabled',false);
//									$('#saveCommentScript').html(result);
									showStatus(SUCCESS,result);

								},

								error : function(err) {
									console.log("not working. ERROR: "+JSON.stringify(err));
									result = "Error";
									showStatus(ERROR,result);
								}

							});

						}

					});

			// Here is code for Quality outline selection

			$('#OutlineAccepOrNeedToImprovementQuality').click(
					function() {

						var vals = $("#outlineQualitAceept").val();

						if (vals === '0') {
							$('#msgQualitytOutline').css({
								'visibility' : 'hidden'
							});

						}

						else if (vals === '1') {
							$('#msgQualitytOutline').css({
								'visibility' : 'hidden'
							});

						} else if (vals === '2') {

							$('#msgQualitytOutline').css({
								'visibility' : 'visible'
							});

						}

					});

			// here is code for Quality Outline Code for submition
/********************* changes made by om prakash ********************************/
			
			$('#submitQualityOutlineStatus').click(function() {

						
						var tutorialId = $("#tutorialId").val();

						var vals = $("#outlineQualitAceept").val();

						if (vals == '0') {
							alert("Accept Or Need To Improvement");

						} else if (vals == '1') {

							$
							.ajax({

								type : "GET",
								url : projectPath+"acceptQualityOutline",
								data : {
								"id" : tutorialId
								},
								contentType : "application/json",
								success : function(result) {
									showStatus(SUCCESS,result);
//									$(
//									"#commentOutlineByQuality")
//									.prop(
//									'disabled',
//									false);
//									$(
//									'#commentOutlineByQuality')
//									.html(
//									result);

								},

								error : function(err) {
									console.log("not working. ERROR: "+ JSON.stringify(err));
									result ="Error occured";
									showStatus(ERROR,result);
								}

							});

						} else if (vals == '2') {

							var msg = $("#msgQualitytOutline").val();

							$
							.ajax({

								type : "GET",
								url : projectPath+"commentByReviewer",
								data : {
									"id" : tutorialId,
									"msg" : msg,
									"type" : "Outline",
									"role" : "Quality"
								},
								contentType : "application/json",
								success : function(
										result) {

//									$(
//									"#commentOutlineByQuality")
//									.prop(
//									'disabled',
//									false);
//									$(
//									'#commentOutlineByQuality')
//									.html(
//									result);
									showStatus(SUCCESS,result);

								},

								error : function(err) {
									console
									.log("not working. ERROR: "
											+ JSON
											.stringify(err));
									result ="Error occured";
									showStatus(ERROR,result);
								}

							});

						}

					});

			// Here is code for slide View into Quality

			$('#slideModaleView')
			.click(
					function() {

						var categoryid = $("#categoryId").val();
						var topicid = $("#topicId").val();
						var lanId = $("#lanId").val();
						$
						.ajax({

							type : "GET",
							url : projectPath+"slidePdfQuality",
							data : {
								"categorname" : categoryid,
								"topicid" : topicid,
								"lanId" : lanId
							},
							contentType : "application/json",
							success : function(result) {
								var res = result;

								source = document
								.getElementById('SlidePdfQuality');
								source.setAttribute(
										'href', res);

							},

							error : function(err) {
								console
								.log("not working. ERROR: "
										+ JSON
										.stringify(err));
							}

						});

					});

			// Slide Accept Or Need To Improvement
//			$('#slideAcceptOrNeedImp').click(function() {
			$('#slideAcceptByQuality').change(function() {

				var vals = $("#slideAcceptByQuality").val();

				if (vals === '2') {
					$('#slideNeedToImp').show();
				}else{
					$('#slideNeedToImp').hide();
				}

//				if (vals === '0') {
//				$('#slideNeedToImp').css({
//				'visibility' : 'hidden'
//				});
//				} else if (vals === '1') {

//				$('#slideNeedToImp').css({
//				'visibility' : 'hidden'
//				});

//				} else if (vals === '2') {

//				$('#slideNeedToImp').css({
//				'visibility' : 'visible'
//				});

//				}

			});

			// Here is code for save comment into slide
			
			/********************* changes made by om prakash ****************************/

			$('#slideAcceptOrNeedToImprovemenetByQuality').click(function() {

						
						var tutorialId = $("#tutorialId").val();
						var vals = $("#slideAcceptByQuality")
						.val();

						if (vals == '0') {

							alert("Select Accept Or Need To Improvement");

							// here is code for Script // here
							// is code for Script
							// css({'visibility':'hidden'});

						} else if (vals == '1') {

							$
							.ajax({

								type : "GET",
								url : projectPath+"acceptQualitySlide",
								data : {
									"id" : tutorialId
								},
								contentType : "application/json",
								success : function(
										result) {

//									alert("result is"+ result);
//									$("#statusSlideByQuality").prop('disabled',false);
//									$('#statusSlideByQuality').html(result);
									showStatus(SUCCESS,result);

								},

								error : function(err) {
									console.log("not working. ERROR: "+ JSON.stringify(err));
									result = "Error";
									showStatus(ERROR,result);
								}

							});

						} else if (vals == '2') {

							$('#slideNeedToImp').css({
								'visibility' : 'visible'
							});

							var msg = $("#slideCommentMsg").val();

							$
							.ajax({

								type : "GET",
								url : projectPath+"commentByReviewer",
								data : {
									"id" : tutorialId,
									"msg" : msg,
									"type" : "Slide",
									"role" : "Quality"
								},
								contentType : "application/json",
								success : function(
										result) {
//									$("#statusSlideByQuality").prop('disabled',false);
//									$('#statusSlideByQuality').html(result);
									showStatus(SUCCESS,result);

								},

								error : function(err) {
									console.log("not working. ERROR: "+ JSON.stringify(err));
									result = "Error";
									showStatus(ERROR,result);
								}

							});

						}

					});

			// here is code for Script for Quality Review
			$('#scriptStatusAccept').click(function() {

				var vals = $("#scriptAccept").val();
				if (vals === '0') {
					$('#msgScriptQuality').css({
						'visibility' : 'hidden'
					});
				} else if (vals === '1') {

					$('#msgScriptQuality').css({
						'visibility' : 'hidden'
					});

				} else if (vals === '2') {

					$('#msgScriptQuality').css({
						'visibility' : 'visible'
					});

				}

			});

			// Here is code for Script for Domain review

//			$('#scriptStatusAcceptDomain').click(function() {
			$('#scriptStatusAcceptDomain').change(function() {

				var vals = $("#scriptAcceptDomain").val();

				if (vals === '2'){
					$('#scriptNeedImprovement').show();
				}else{
					$('#scriptNeedImprovement').hide();
				}

//				if (vals === '0') {
//				$('#msgScriptDomain').css({
//				'visibility' : 'hidden'
//				});
//				} else if (vals === '1') {

//				$('#msgScriptDomain').css({
//				'visibility' : 'hidden'
//				});

//				} else if (vals === '2') {

//				$('#msgScriptDomain').css({
//				'visibility' : 'visible'
//				});

//				}

			});

			// Here is code for Accept Or Need To Improvement

			// Here is code for keyword Quality Review


//			$('#keywordAccepOrNeedToImprovementQuality').click(
			$('#keywordQualitAceept').change(
					function() {
						console.log('changed');
						var vals = $("#keywordQualitAceept").val();
						if (vals === '2'){
							$('#NeedImprovementKeyword').show();
						}
//						if (vals === '0') {

//						$('#msgkeywordQuality').css({
//						'visibility' : 'hidden'
//						});

//						} else if (vals === '1') {

//						$('#msgkeywordQuality').css({
//						'visibility' : 'hidden'
//						});

//						} else if (vals === '2') {

//						$('#msgkeywordQuality').css({
//						'visibility' : 'visible'
//						});

//						}

					});
			$('#graphicsQuality').change(
					function() {
						console.log('changed');
						var vals = $("#graphicsQuality").val();
						if (vals === '2'){
							$('#graphicsNeedImprovementByQuality').show();
						}
					});

			// here is code for Video review into Quality

//			$('#VideoAccepOrNeedToImprovementQuality').click(
			$('#videoQualitAceept').change(
					function() {

						var vals = $("#videoQualitAceept").val();

						if (vals === '2') {
							$('#NeedImprovement').show();
						}else{
							$('#NeedImprovement').hide();
						}
//						if (vals === '0') {

//						$('#msgVideoQuality').css({
//						'visibility' : 'hidden'
//						});

//						} else if (vals === '1') {

//						$('#msgVideoQuality').css({
//						'visibility' : 'hidden'
//						});

//						} else if (vals === '2') {

//						$('#msgVideoQuality').css({
//						'visibility' : 'visible'
//						});

//						}

					});

			// Here is code for Domain review -Slide

//			$('#SlideStatusAccept').click(function() {
			$('#SlideAcceptDomain').change(function() {

				var vals = $("#SlideAcceptDomain").val();

				if (vals === '2') {
					$('#slideNeedImprovement').show();
				}else{
					$('#slideNeedImprovement').hide();
				}
//				if (vals === '0') {

//				$('#slideNeedImprovement').css({
//				'visibility' : 'hidden'
//				});

//				} else if (vals === '1') {

//				$('#slideNeedImprovement').css({
//				'visibility' : 'hidden'
//				});

//				} else if (vals === '2') {

//				$('#slideNeedImprovement').css({
//				'visibility' : 'visible'
//				});

//				}

			});
			$('#graphicsDomain').change(function() {

				var vals = $("#graphicsDomain").val();

				if (vals === '2') {
					$('#graphicsNeedImprovement').show();
				}else{
					$('#graphicsNeedImprovement').hide();
				}

			});

			/**************** changes made by om prakash ******************************/
			
			$('#slideAcceptOrNeedToImprovemenetByDomain').click(function() {
						
						var tutorialId = $("#tutorialId").val();

						var vals = $("#SlideAcceptDomain").val();

						if (vals == '0') {

							alert("Select Accept Or Need To Improvement");

							// here is code for Script // here
							// is code for Script
							// css({'visibility':'hidden'});

						} else if (vals == '1') {

							$
							.ajax({

								type : "GET",
								url : projectPath+"acceptDomainSlide",
								data : {
								"id" : tutorialId
								},
								contentType : "application/json",
								success : function(result) {
//									$("#statusSlideByDomain").prop('disabled',false);
//									$('#statusSlideByDomain').html(result);
									showStatus(SUCCESS, result);

								},

								error : function(err) {
									console.log("not working. ERROR: "+ JSON.stringify(err));
									result = "Error";
									showStatus(ERROR, result);
								}

							});

						} else if (vals == '2') {

							var msg = $("#slideCommentMsg").val();
							$.ajax({
								type : "GET",
								url : projectPath+"commentByReviewer",
								data : {
									"id" : tutorialId,
									"msg" : msg,
									"type" : "Slide",
									"role" : "Domain"
								},
								contentType : "application/json",
								success : function(
										result) {

//									$("#statusSlideByDomain").prop('disabled',false);
//									$('#statusKeywordByQuality').html(result);
									showStatus(SUCCESS, result);
								},

								error : function(err) {
									console.log("not working. ERROR: "+ JSON.stringify(err));
									showStatus(ERROR, result);
								}

							});

						}

					});
/**********************  changes made by om prakash ******************************/

			// here is code for Accept Or Need To Improvement Script
/************************ changes made by om prakash *****************************/
			
			$('#KeyWordAcceptOrNeedToImprovemenetByQuality').click(function() {
//						
						var tutorialId = $("#tutorialId").val();

						var vals = $("#keywordQualitAceept").val();

						if (vals == '0') {

							alert("Select Accept Or Need To Improvement");

							// here is code for Script // here
							// is code for Script
							// css({'visibility':'hidden'});

						} else if (vals == '1') {

							$
							.ajax({

								type : "GET",
								url : projectPath+"acceptQualityKeywords",
								data : {
									"id" : tutorialId
								},
								contentType : "application/json",
								success : function(
										result) {
									showStatus(SUCCESS,result);
//									$("#statusKeywordByQuality").prop('disabled',false);
//									$('#statusKeywordByQuality').html(result);

								},

								error : function(err) {
									console.log("not working. ERROR: "+ JSON.stringify(err));
									result = "Error";
									showStatus(ERROR,result);
								}

							});

						} else if (vals == '2') {
							$('#NeedImprovementKeyword').prop('readonly', true);
							var msg = $("#msgkeywordQuality").val();

							$.ajax({

								type : "GET",
								url : projectPath+"commentByReviewer",
								data : {
									"id" : tutorialId,
									"msg" : msg,
									"type" : "Keyword",
									"role" : "Quality"
								},
								contentType : "application/json",
								success : function(
										result) {

//									$("#statusKeywordByQuality").prop('disabled',false);
//									$('#statusKeywordByQuality').html(result);
									showStatus(SUCCESS,result);
								},

								error : function(err) {
									console.log("not working. ERROR: "+ JSON.stringify(err));
									result = "Error";
									showStatus(ERROR,result);
								}

							});

						}

					});

			//


	

			/*Here is code for Contributor PreRequistic component  display topic */

			$('#catAllID').change(function(){

				var selectedCategoryId=$(this).find(":selected").val();
				var lanId =$("#lanId").val();
				$.ajax({
					type: "GET",
					url: projectPath+"loadTopicByPreRequistic",
					data: { "id": selectedCategoryId, "lanId":lanId},
					contentType: "application/json",
					success: function (result)
					{

						var html = '';
						var len = result.length;
						html += '<option value="0"></option>';
						for (var i = 0; i < len; i++) {
							html += '<option value="' + result[i] + '">'
							+ result[i]
							+ '</option>';
						}
						html += '</option>';

						$("#inputTopicPre").prop('disabled',false);
						$('#inputTopicPre').html(html);

					},

					error : function(err){
						console.log("not working. ERROR: "+JSON.stringify(err));
					}

				});

			});


			// Here is code for upload preRequistic link path

			$('#preRequistic').click(function()
					{

				var selectedCategoryId=$(this).find(":selected").val();
				var lanId =$("#lanId").val();
				var inputTopicPre =$("#inputTopicPre").val();


				alert(inputTopicPre);

				$.ajax({
					type: "GET",
					url: projectPath+"loadTopicByPreRequistic",
					data: { "id": selectedCategoryId, "lanId":lanId},
					contentType: "application/json",
					success: function (result)
					{

						alert("save information");

						var html = '';
						var len = result.length;
						html += '<option value="0">Select Topic</option>';
						for (var i = 0; i < len; i++) {
							html += '<option value="' + result[i] + '">'
							+ result[i]
							+ '</option>';
						}
						html += '</option>';

						$("#inputTopicPre").prop('disabled',false);
						$('#inputTopicPre').html(html);

					},

					error : function(err){
						console.log("not working. ERROR: "+JSON.stringify(err));
					}

				});

					});



			/*Here is code for Contributor PreRequistic component  display topic */

			$('#catAllID').change(function(){

				var selectedCategoryId=$(this).find(":selected").val();
				var lanId =$("#lanId").val();
				$.ajax({
					type: "GET",
					url: projectPath+"loadTopicByPreRequistic",
					data: { "id": selectedCategoryId, "lanId":lanId},
					contentType: "application/json",
					success: function (result)
					{

						var html = '';
						var len = result.length;
						html += '<option value="0"></option>';
						for (var i = 0; i < len; i++) {
							html += '<option value="' + result[i] + '">'
							+ result[i]
							+ '</option>';
						}
						html += '</option>';

						$("#inputTopicPre").prop('disabled',false);
						$('#inputTopicPre').html(html);

					},

					error : function(err){
						console.log("not working. ERROR: "+JSON.stringify(err));
					}

				});

			});




			// Here is code for upload preRequistic link path

			$('#preRequistic').click(function()
					{

				var selectedCategoryId=$(this).find(":selected").val();
				var lanId =$("#lanId").val();
				var inputTopicPre =$("#inputTopicPre").val();


				alert(inputTopicPre);

				$.ajax({
					type: "GET",
					url: projectPath+"loadTopicByPreRequistic",
					data: { "id": selectedCategoryId, "lanId":lanId},
					contentType: "application/json",
					success: function (result)
					{

						alert("save information");

						var html = '';
						var len = result.length;
						html += '<option value="0">Select Topic</option>';
						for (var i = 0; i < len; i++) {
							html += '<option value="' + result[i] + '">'
							+ result[i]
							+ '</option>';
						}
						html += '</option>';

						$("#inputTopicPre").prop('disabled',false);
						$('#inputTopicPre').html(html);

					},

					error : function(err){
						console.log("not working. ERROR: "+JSON.stringify(err));
					}

				});

					});

/*********************** chaanges made by om prakash ************************************/
			
			$('#VideoAcceptOrNeedToImprovemenetByQuality').click(function() {
						// chage

						
						var tutorialId = $("#tutorialId").val();

						var vals = $("#videoQualitAceept")
						.val();

						if (vals == '0') {

							alert("Select Accept Or Need To Improvement");

							// here is code for Script // here
							// is code for Script
							// css({'visibility':'hidden'});

						} else if (vals == '1') {

							$
							.ajax({

								type : "GET",
								url : projectPath+"acceptQualityVideo",
								data : {
									"id" : tutorialId
								},
								contentType : "application/json",
								success : function(result) {

									$("#statusQualityByQuality").prop('disabled',false);
									$('#statusQualityByQuality').html(result);
									showStatus(SUCCESS,result);

								},

								error : function(err) {
									console
									.log("not working. ERROR: "
											+ JSON
											.stringify(err));
									result = "Error";
									showStatus(ERROR,result);
								}

							});

						} else if (vals == '2') {

							var msg = $("#msgVideoQuality")
							.val();

							$
							.ajax({

								type : "GET",
								url : projectPath+"commentByReviewer",
								data : {
									"id" : tutorialId,
									"msg" : msg,
									"type" : "Video",
									"role" : "Quality"
								},
								contentType : "application/json",
								success : function(
										result) {

									$("#statusQualityByQuality").prop(
													'disabled',
													false);
									$(
									'#statusQualityByQuality')
									.html(
											result);

									showStatus(SUCCESS,result);

								},

								error : function(err) {
									console
									.log("not working. ERROR: "
											+ JSON
											.stringify(err));
									result = "Error";
									showStatus(ERROR,result);
								}

							});

						}

					});

			$(".c-modal-close").click(function() {
				console.log('this');
				$(".modal").removeClass("is-active");
			});

/********* Changes made by Om Prakash ************************/

			$(".comment-contri").click(function() {
				console.log('this');
				component = this.id;
				var elem_id = '#'+component+'_comment'
				var tutorialId = $("#tutorialId").val();
				
				var msg = $(elem_id).val();
				$.ajax({
					type : "GET",
					url : projectPath+"commentByContributor",
					data : {
						"id" :tutorialId,
						"type":component,
						'msg' : msg
					},
					contentType : "application/json",
					success : function(result) {
						
						$('.commentS').addClass('d-block');
						 $('.commentS').html(result);
					},

					error : function(err) {
						console.log("not working. ERROR: "+ JSON.stringify(err));

					}
				});

			});

			$("#show_all_consultants").click(function() {
				console.log('this');
				$.ajax({
					type: "GET",
					url: projectPath+"displayConsultants",
					contentType: "application/json",
					success: function (result){
						console.log(result);
						$('.cons_record').show();
						var id = result[0].id;
						var name = result[0].nameConsaltant;
						var desc = result[0].descriptionConsaltant;
						var img = result[0].uploadConsaltantImage;
						var editLink = 'productconsalantant/edit/' + id;
						var elink = '<a href="'+editLink+'">Edit</a>';
						var deleteLink = '/consalantant/delete/' + id;
						var dlink = '<a onclick="submitForm()" href="'+deleteLink+'">Delete</a>';

						var markup = "<tr><td>" + id + "</td><td>" + name + "</td><td>"+desc+"</td><td>"+"image"+"</td><td>"+elink+"</td><td>"+dlink+"</td></tr>";

						console.log(elink);
						console.log(dlink);
						console.log(markup);
						$("table tbody").append(markup);

					},

					error : function(err){
						console.log("not working. ERROR: "+JSON.stringify(err));
					}
				});
			});


			$('#categorySelect').on(
					'change',
					function() {
						var category = $(this).val();

						$.ajax({
							type : "GET",
							url : projectPath+"listTopicByCategory",
							data : {
								"category" : category
							},
							contentType : "application/json",
							success : function(result) {

								var html = '';
								var len = result.length;

								for (var i = 0; i < len; i++) {
									html += '<option value="'
										+ result[i] + '">'
										+ result[i] + '</option>';
								}
								html += '</option>';

								$("#topicSelect")
								.prop('disabled', false);
								$('#inputTopic').html(html);

							},
							error : function(err) {
								console.log("not working. ERROR: "
										+ JSON.stringify(err));
							}
						});

					});

			$(".logoToUpload").on('change', function() {

				var fileSize = this.files[0].size;
				if(fileSize > 500000){
					alert("File size should be less than 5kB");
					this.value="";
					return false;
				}
			});
			
			
			
			/*$('#eventCategory').on('change', function() {
				category_id = this.value;
//				  alert( this.value );
				$.ajax({

					type : "GET",
					url : projectPath+"loadEventByCategory",
					data : {
						"id" : catgoryid
					},
					contentType : "application/json",
					success : function(result) {

					//	html += '<option value="'+ result[i]+ '">'+ result[i]+ '</option>';

						var html = '';
						var len = result.length;
						html += '<option value="0">Select Topic</option>';
						$.each(result , function( key, value ) {
	  	  			        html += '<option value=' + key + '>'+ value  + '</option>';
	  	  			     })
						html += '</option>';

						$("#inputTopicContributor").prop('disabled',false);
						$('#inputTopicContributor').html(html);

					},

					error : function(err) {
						console.log("not working. ERROR: "	+ JSON.stringify(err));
					}

				});
				
				});*/

			$("#addNullPrerequisite").click(function() {
				console.log("this.......");
				component = this.id;
				var elem_id = '#'+component+'_comment'
				var tutorialId = $("#tutorialId").val();
				
				var msg = $(elem_id).val();
				$.ajax({
					type : "GET",
					url : projectPath+"addPreRequisticWhenNotRequired",
					data : {
						"id" :tutorialId
					},
					contentType : "application/json",
					success : function(result) {
						var msg = 'Pre-Requistic updated successfully.'
									// $('#statusOutline').html(html);
									$('#statusPreReq')
									.addClass(
									'd-block');
								$('#statusPreReq').html(msg);
					},

					error : function(err) {
						alert('error');
						console.log("not working. ERROR: "+ JSON.stringify(err));
							var msg = 'Error in updating Pre-Requistic.'
									// $('#statusOutline').html(html);
									$('#statusPreReq').addClass('d-block');
								 $('#statusPreReq').addClass('alert-danger');

								$('#statusPreReq').html(msg);

					}
				});

			});
			
//			$(".select_language").on('click',function(){
//			
//				console.log("select_language.......",this);
//				component = this;
//				$.ajax({
//					type : "GET",
//					url : projectPath+"loadLanguages",
//					data : {
////						"id" :tutorialId
//					},
//					contentType : "application/json",
//					success : function(result) {
//						$(component).html('');
//						for (lang in result){
//							$(component).append("<option>"+result[lang]+"</option>");
//						}
//					},
//
//					error : function(err) {
//						alert('error');
//						console.log("not working. ERROR: "+ JSON.stringify(err));
//
//					}
//				});
//
//			});
//			
			
			

		});


function validateEmail($email) {
	  var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	  return emailReg.test( $email );
}

function loadAllLanguages() {
	console.log("select_language.......",this);
	component = this;
	$.ajax({
		type : "GET",
		url : projectPath+"loadLanguages",
		data : {
//			"id" :tutorialId
		},
		contentType : "application/json",
		success : function(result) {
			$(component).html('');
			for (lang in result){
				$(component).append("<option>"+result[lang]+"</option>");
			}
		},

		error : function(err) {
			alert('error');
			console.log("not working. ERROR: "+ JSON.stringify(err));

		}
	});
}

/*---------------------------------------Profile picture update Ajax call-------------------------------*/

function updateProfilePicture(){
	
	var form=$('#uploadProfilePic')[0];
	var data=new FormData(form);
	
	
	var urlPassed;
	
	urlPassed= projectPath+"updateProfilePic";


		$.ajax({
			type: "POST",
			enctype: 'multipart/form-data',
			url: urlPassed,
			data:data,
			cache:false,
			contentType:false,
			processData:false,
			timeout: 600000,
			success:function(data){
				
				 $('#chngProfilePic').prop('disabled',true);
				 $('#profileText').css({"display": "block"});
				
			
			},
		
		error : function(err){
			console.log("not working. ERROR: "+JSON.stringify(err));
			}

	});
	
	
}


function readImageUrl(input) {
	  if (input.files && input.files[0]) {
	    var reader = new FileReader();
	    
	    reader.onload = function(e) {
	      $('#pictureShow').attr('src', e.target.result);
	    }
	    
	    reader.readAsDataURL(input.files[0]); // convert to base64 string
	  }
	}
function validate_file_size(elem,s){
	console.log(elem.files[0].size);
	console.log(s);
	var fileSize = elem.files[0].size;
	if(fileSize > s){
		alert("Please check file size");
		elem.value="";
		return false;
	}
}

/* here is code for download question */

//$('#inputLanguage1').change(function(){
//$.ajax({
//type: "GET",
//url: "/loadLanguageByUser",
//data: { "id": userContributor},
//contentType: "application/json",
//success: function (result){


//var html = '';
//var len = result.length;
//html += '<option value="0">Select Language</option>';
//for (var i = 0; i < len; i++) {
//html += '<option value="' + result[i] + '">'
//+ result[i]
//+ '</option>';
//}
//html += '</option>';

//$("#lanId").prop('disabled',false);
//$('#lanId').html(html);

//},

//error : function(err){
//console.log("not working. ERROR: "+JSON.stringify(err));
//}
//});



//});