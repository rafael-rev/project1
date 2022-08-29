// ********GLOBALS********
let newRequestContainer = document.getElementById("newrequest-container");

// ********WINDOW LOADER*******
window.onload = async () => {
    let response = await fetch("http://localhost:8999/session");
    let responseBody = await response.json();
    if(!responseBody.success){
        console.log("Session not found");
        // redir to login
        window.location = "../";
    }
    displayRequestForm();
}

// ********FORM FUNCTIONALITY******
let reqFormElem = document.createElement("form");
reqFormElem.id = "newrequest-form";
reqFormElem.addEventListener("submit", (event) => {
    event.preventDefault();
    let newReqInput = {
        "amount": document.getElementById("amount-input").value,
        "description": document.getElementById("description-input").value,
        "type": document.querySelector('input[name="type-select"]:checked').value
    }
    console.log(newReqInput);
    sendNewRequest(newReqInput);
})

// *******NEW REQUEST FUNCTION********
async function sendNewRequest(requestInput){
    let response = await fetch("http://localhost:8999/api/user", {
        method: "POST",
        body: JSON.stringify(requestInput)
    });
    //let data = await response.text();
    //console.log(data);
    let responseBody = await response.json();
    let title = document.getElementById("newrequest-info");
    if(responseBody.success){
        console.log(responseBody.message);
        title.style.color = "green";
        title.innerText = responseBody.message;
    }else{
        title.style.color = "red";
        title.innerText = responseBody.message;
        console.log(responseBody.message);
    }
}

// *******HTML CONSTRUCTION********
// Radio Button Values list
let radioTypesArr = ["lodging", "travel", "food", "other"];

function displayRequestForm(){
    // REQUEST CONTAINER & FORM
    let reqBoxElem = document.createElement("div");
    reqBoxElem.id = "newrequest-box";

    let reqBoxTitleElem = document.createElement("div");
    reqBoxTitleElem.id = "newrequest-info";
    reqBoxTitleElem.innerText = "Enter your request"

    let inpAmtFieldElem = document.createElement("input");
    inpAmtFieldElem.type = "number";
    inpAmtFieldElem.className = "fields";
    inpAmtFieldElem.id = "amount-input";
    inpAmtFieldElem.step = "0.01";
    inpAmtFieldElem.min = 0;
    inpAmtFieldElem.placeholder = "enter amount";

    let inpDescrFieldElem = document.createElement("input");
    inpDescrFieldElem.type = "text";
    inpDescrFieldElem.className = "fields";
    inpDescrFieldElem.id = "description-input";
    inpDescrFieldElem.placeholder = "enter description";
    // RADIO BUTTONS
    let radioContainerElem = document.createElement("div");
    radioContainerElem.id = "type-selector-container";

    for(i=0; i < radioTypesArr.length; i++){
        let radioLabelSpan = document.createElement("span");
        radioLabelSpan.className = "radio-label";
        radioLabelSpan.innerText = radioTypesArr[i];
        let radioElem = document.createElement("input");
        radioElem.type = "radio";
        radioElem.name = "type-select";
        radioElem.value = radioTypesArr[i];
        radioContainerElem.append(radioElem, radioLabelSpan);
    }
    // SUBMIT / REQUEST BUTTONS
    let buttonContainerElem = document.createElement("div");
    buttonContainerElem.id = "request-form-buttons";

    let submitButtonElem = document.createElement("input");
    submitButtonElem.classList.add("btn", "btn-success");
    submitButtonElem.type = "submit";

    let cancelButtonElem = document.createElement("button");
    cancelButtonElem.classList.add("btn", "btn-danger");
    cancelButtonElem.innerText = "Cancel"
    cancelButtonElem.addEventListener("click", () => {
        window.location = "../user";
    })
    // APPEND
    reqFormElem.appendChild(inpAmtFieldElem);
    reqFormElem.appendChild(inpDescrFieldElem);
    reqFormElem.appendChild(radioContainerElem);
    reqFormElem.appendChild(buttonContainerElem);
    buttonContainerElem.appendChild(submitButtonElem);
    buttonContainerElem.appendChild(cancelButtonElem);

    reqBoxElem.appendChild(reqBoxTitleElem);
    reqBoxElem.appendChild(reqFormElem);
    
    newRequestContainer.appendChild(reqBoxElem);
}