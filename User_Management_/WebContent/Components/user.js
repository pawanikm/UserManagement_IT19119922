$(document).ready(function()
{ 
	if ($("#alertSuccess").text().trim() == "") 
	 { 
	 $("#alertSuccess").hide(); 
	 } 
	 $("#alertError").hide(); 
}); 

// SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{ 
	// Clear alerts---------------------
	 $("#alertSuccess").text(""); 
	 $("#alertSuccess").hide(); 
	 $("#alertError").text(""); 
	 $("#alertError").hide(); 
	// Form validation-------------------
	var status = validateUserForm(); 
	if (status != true) 
	 { 
		 $("#alertError").text(status); 
		 $("#alertError").show(); 
	 return; 
	 } 
	 
	// If valid------------------------
	var type = ($("#hidUserIDSave").val() == "") ? "POST" : "PUT"; 
	 $.ajax( 
	 { 
		 url : "userAPI", 
		 type : type, 
		 data : $("#formUsers").serialize(), 
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 	onUserSaveComplete(response.responseText, status); 
		 } 
	 }); 
});
 
function onUserSaveComplete(response, status)
{ 
	if (status == "success") 
	 { 
	 	var resultSet = JSON.parse(response); 
	 	if (resultSet.status.trim() == "success") 
	 { 
	 	$("#alertSuccess").text("Successfully saved."); 
	 	$("#alertSuccess").show(); 
	 	$("#divUserGrid").html(resultSet.data); 
	 } else if (resultSet.status.trim() == "error") 
	 { 
	 	$("#alertError").text(resultSet.data); 
	 	$("#alertError").show(); 
	 } 
	 } else if (status == "error") 
	 { 
	 	$("#alertError").text("Error while saving."); 
	 	$("#alertError").show(); 
	 } else
	 { 
	 	$("#alertError").text("Unknown error while saving.."); 
	 	$("#alertError").show(); 
	 } 
	 	$("#hidUserIDSave").val(""); 
	 	$("#formUsers")[0].reset(); 
}

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{ 
	 $("#hidUserIDSave").val($(this).data("id")); 
	 $("#fname").val($(this).closest("tr").find('td:eq(0)').text()); 
	 $("#lname").val($(this).closest("tr").find('td:eq(1)').text()); 
	 $("#address").val($(this).closest("tr").find('td:eq(2)').text()); 
	 $("#email").val($(this).closest("tr").find('td:eq(3)').text()); 
	 $("#contact").val($(this).closest("tr").find('td:eq(4)').text()); 
	 $("#username").val($(this).closest("tr").find('td:eq(5)').text()); 
	 $("#pwd").val($(this).closest("tr").find('td:eq(6)').text());
	 
});

// REMOVE==========================================
$(document).on("click", ".btnRemove", function(event)
{ 
	 $.ajax( 
		 { 
			 url : "userAPI", 
			 type : "DELETE", 
			 data : "id=" + $(this).data("id"),
			 dataType : "text", 
			 complete : function(response, status) 
			 { 
			 	onUserDeleteComplete(response.responseText, status); 
			 } 
		 }); 
	});

function onUserDeleteComplete(response, status)
{ 
	if (status == "success") 
	 { 
		 var resultSet = JSON.parse(response); 
		 if (resultSet.status.trim() == "success") 
	 { 
		 $("#alertSuccess").text("Successfully deleted."); 
		 $("#alertSuccess").show(); 
		 $("#divUserGrid").html(resultSet.data); 
	 } else if (resultSet.status.trim() == "error") 
	 { 
		 $("#alertError").text(resultSet.data); 
		 $("#alertError").show(); 
	 } 
	 } else if (status == "error") 
	 { 
		 $("#alertError").text("Error while deleting."); 
		 $("#alertError").show(); 
	 } else
	 { 
		 $("#alertError").text("Unknown error while deleting.."); 
		 $("#alertError").show(); 
	 } 
}


// CLIENT-MODEL================================================================
function validateUserForm() 
{ 
	// FName
	if ($("#fname").val().trim() == "") 
	 { 
	 	return "Insert First Name."; 
	 } 
	// Lname
	if ($("#lname").val().trim() == "") 
	 { 
	 	return "Insert Last Name."; 
 	} 
 
	// Address-------------------------------
	if ($("#address").val().trim() == "") 
	 { 
		 return "Insert Address."; 
	 } 
	 
	 // Email-------------------------------
	if ($("#email").val().trim() == "") 
	 { 
		 return "Insert Email."; 
	 } 
	 
	 // Contact-------------------------------
	if ($("#contact").val().trim() == "") 
	 { 
		 return "Insert Contact."; 
	 } 
	 
	 // Username-------------------------------
	if ($("#username").val().trim() == "") 
	 { 
		 return "Insert Username."; 
	 } 
	  
	// Password------------------------
	if ($("#pwd").val().trim() == "") 
	 { 
	 	return "Insert Password."; 
 	} 
return true; 
}