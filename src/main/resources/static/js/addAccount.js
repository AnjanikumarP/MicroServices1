
			var cardObject;	

	$("#creatingAccount").on('click',function(event){
	event.preventDefault();
		var AccountObj = {
			holderName: $("#holderName").val(),
			accountType : $("#accountType").val(),
			balance:$("#balance").val()
		}
		var AccountObjAsJSON = JSON.stringify(AccountObj);
		console.log(AccountObjAsJSON);
				$.ajax({
					url : "/bank/newAccount",
					type : "POST",
					data : AccountObjAsJSON,
					contentType : "application/json",
					success : function(response, status) {
						if (response == "successAccount") {
							alert("Account Successfully Added");
							window.location = "/bank/admin";
						}else 
							{
							alert("Account Add failed...Try Again ");
							}
						

					}

				})

			})
			
			
			
			
$('.open-card-modal').on('submit', function(event){
	$('#createCardModal').modal('show');
	event.preventDefault();
	
	cardObject = {
		accountNo : $(this).find("input")[0].value
	};
	console.log(cardObject.accountNo);
});
	
	

	$("#issue-card-form").submit(function(event) {
		event.preventDefault();
	    
		cardObject.cardType = $("#cardType").val();
		cardObject.maximumLimit = $("#limit").val();
		
		if(cardObject.cardType.toUpperCase() == "DEBIT" || cardObject.cardType.toUpperCase() == "CREDIT") {
			var cardObjectJson = JSON.stringify(cardObject);
			
			var request = $.ajax({
				url : "/bank/newcard",
				method : "POST",
				data : cardObjectJson,
				contentType : "application/json",
				
				success:function(data) {
					
					if (data != null) {
						alert("Card SuccessFully Added for Account :"+data);
						window.location.reload();
					}
					else {
						alert("Card creation failed");
					}},
				error:function(jqXHR,textStatus){console.log(textStatus);}
			})
		}
		else {
			alert("Card type is not correct");
		}
		cardObject = null;
	})
	
	
	
$('.view-card').on('submit', function(event){
	event.preventDefault();
	
	 var cardDetail = {
		accountNo : $(this).find("input")[0].value
	};
	console.log(cardDetail.accountNo);
	
	$('#accountNo').attr('accountNumber', cardDetail.accountNo);
	$.ajax({
		url : "/bank/fetchCards/"+cardDetail.accountNo,
		type : "GET",
		success :window.location="/bank/fetchCards/"+cardDetail.accountNo, 
			function(request) {
			console.log(request);
		}

	})
});	