// 2. set up needed globals
let user;
let requests;
let totalItemContainer = document.getElementById("item-container"); //contains items
let controlPanel = document.getElementById("control-panel"); // contains control buttons
let detailPanel = document.getElementById("detail-panel"); // contains details for a req
let overallDisplay = document.getElementById("main-display"); // contains totalItemContainer & detailPanel
const itemsArray = ["item-id", "item-author", "item-value", "item-submitted", "item-description", "item-type", "item-status"];
let currentSelectedItem; // stores the currently selected item
let targetItemID = 1;   // stores the currently selected item's ID
// 3. set up the window load to check for session. If no session redir to login page
//      else, save the user and requests to the globals
window.onload = async () => {
    let response = await fetch("http://localhost:8999/session");
    let responseBody = await response.json();
    if(!responseBody.success){
        console.log("Session not found");
        // redir to login
        window.location = "../";
    }else{
        user = responseBody.data;
        requests = await getRequests();
    }
    displayPage(requests);
}
// FUNCTION TO GET ALL REIMB REQUESTS IN SYSTEM
async function getRequests(){
    let response = await fetch("http://localhost:8999/api/manager");
    let responseBody = await response.json();
    return responseBody.data;
}
// FUNCTIONS TO FILTER REIMBURSEMENTS BY STATUS
function parsePendingFromRequests(){
    outputArr = [];
    requests.forEach((request) => {
        if(request["status"] === "pending"){
            outputArr.push(request);
        }
    });
    return outputArr;
}
function parseApprovedFromRequests(){
    outputArr = [];
    requests.forEach((request) => {
        if(request["status"] === "approved"){
            outputArr.push(request);
        }
    });
    return outputArr;
}
function parseRejectedFromRequests(){
    outputArr = [];
    requests.forEach((request) => {
        if(request["status"] === "denied"){
            outputArr.push(request);
        }
    });
    return outputArr;
}
// NOTE: look here
// function restoreAssessMessage(oldmsg){
//     let text = document.getElementById("detail-panel-title-text");
//     text.innerText = oldmsg;
//     console.log("restored");
// }

// Custom pause function, must be used inside async functions only
// A Promise is wrapped around the callback to setTimeout, which is apparently
//  incompatable with async functions by itself
// the callback to setTimeout waits for "delay" amount of time in ms
// effectively "pauses" execution of code within the async function until the delay is up
// LEARNED FROM: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Using_promises
const waitfor = (delay) => new Promise((resolve) => setTimeout(resolve, delay));

// iterates through the requests retrieved onload and for each request/item:
// - constructs a div containing 6 divs 
//      -each inner div contains the text associated with 
//       the select identifying data column names
// - adds an eventlistener to each item, so its data can be "selected" and 
//      - operated on by the accept/reject buttons
//      - populate the "detail panel" with information pertinent to the request, including
//          the employee's full name, which isn't included in the items
function requestLister(requests){
    let detailKeyArray = ["id", "author", "amount", "time_submitted", "description", "type", "status"];

    requests.forEach(request => {
        // TARGET: totalItemContainer
        // Single ITEM inside the totalItemContainer
        let itemElem = document.createElement("div");
        itemElem.className = "item";
        // The 6 details to populate the ITEM
        for(i = 0; i < itemsArray.length; i++){
            let itemDetailElem = document.createElement("div");
            itemDetailElem.className = "item-detail";
            itemDetailElem.id = itemsArray[i];
            let key = detailKeyArray[i];
            let currentData = request[key];
            // Formatting all "amount" number values to display decimal places
            if(itemsArray[i] == "item-value"){
                currentData = currentData.toFixed(2)
            }
            // color coding pending/approved/rejected status
            itemDetailElem.innerText = currentData;
            if(currentData=="pending"){
                itemDetailElem.style.color = "yellow";
                itemDetailElem.style.fontWeight = "bold";
            }else if(currentData=="denied"){
                itemDetailElem.style.color = "red";
            }
            itemElem.appendChild(itemDetailElem);
        }
        // EVENT "CLICK" Function for individual items
        // used to populate the Details Panel on the left and set the current item so the buttons can work on it
        itemElem.addEventListener("click", async (event) => {
                event.stopPropagation();
                let target = event.currentTarget;
                // Gets the userID from the Item
                let itemAuthorDiv = target.querySelectorAll("#item-author");
                console.log(`USERID: ${itemAuthorDiv[0].innerText}`); 
                let targetUserID = itemAuthorDiv[0].innerText;
                // Gets the selected Item's ID
                let displayData = target.querySelectorAll("#item-id");
                targetItemID = displayData[0].innerText;
                console.log(`INCLICK ${targetItemID}`)
                let targetItem;  // var to store the full Item that matches the condition by ID# -- store for function and globally for other work (assess buttons)
                requests.forEach(request =>{
                    if(request["id"] == targetItemID){
                        targetItem = request;
                        currentSelectedItem = request;
                    }
                })
                let displayCellElems = document.querySelectorAll(".table-data-text");
                // for(i=0;i < detailKeyArray.length; i++)
                displayCellElems[0].innerText = targetItem["id"]; // ID field
                displayCellElems[1].innerText = await getFullName(targetUserID);  // Employee field
                displayCellElems[2].innerText = targetItem["amount"];
                displayCellElems[3].innerText = targetItem["time_submitted"];
                displayCellElems[4].innerText = targetItem["description"];
                displayCellElems[5].innerText = targetItem["type"];
                displayCellElems[6].innerText = targetItem["status"];   
        });
        // After adding the details, append the item to the displayContainer
        totalItemContainer.appendChild(itemElem);
    });
}

// Quickly get the Full Name of the given Employee ID number
async function getFullName(id){
    let response = await fetch(`/api/manager/${id}`, {
        method: "GET"
    });
    let responseBody = await response.text();
    return responseBody;
}

// APPROVE/REJECT REQUEST FUNCTION
// applied to both the Approve and Reject buttons
// accept_code can only be 0 (false) or 1 (true), since the backend will modify 
// it's response based on only these values
async function assessRequest(accept_code){
    let request = {
        "id":parseInt(targetItemID),
        "accept":accept_code
    }
    let response = await fetch(`/api/manager?id=${targetItemID}&accept=${accept_code}`,{
        method: "PATCH",
        body: JSON.stringify(request)
    });
    let responseBody = await response.json();
    return responseBody;
}

// FUNCTION TO BUILD THE PAGE
// the APPROVE/REJECT buttons are the asynchronous part
async function displayPage(){
    // Left Details Panel elements
    let detailPanelTitleContainer = document.createElement("div");
    detailPanelTitleContainer.id = "detail-panel-title";
    detailPanel.appendChild(detailPanelTitleContainer);
    let detailPanelTitle_Text = document.createElement("h3");
    detailPanelTitle_Text.id = "detail-panel-title-text";
    detailPanelTitle_Text.innerText = "Current Selection";
    detailPanelTitleContainer.appendChild(detailPanelTitle_Text);
    let detailPanelTextContainer = document.createElement("div");
    detailPanelTextContainer.id = "detail-panel-text";
    detailPanel.appendChild(detailPanelTextContainer);
    let detailPanelTextHeaders = document.createElement("div");
    detailPanelTextHeaders.id = "headers";
    detailPanelTextContainer.appendChild(detailPanelTextHeaders);
    let detailPanelData = document.createElement("div");
    detailPanelData.id = "detail-data";
    detailPanelTextContainer.appendChild(detailPanelData);
    let headersListElem = document.createElement("div");
    headersListElem.className = "listbox";
    let dataListElem = document.createElement("div");
    dataListElem.className = "listbox";
    detailPanelTextHeaders.append(headersListElem);
    detailPanelData.append(dataListElem);
    // assess buttons
    let assessButtonContainer = document.createElement("div");
    assessButtonContainer.id = "app-rej-button-container";
    let acceptButton = document.createElement("button");
    acceptButton.id = "item-approve-btn";
    acceptButton.className = "assess-btn";
    acceptButton.classList.add("btn", "btn-success");
    acceptButton.innerText = "Approve";
    acceptButton.addEventListener("click", async (event) => {
        // First makes asynchronous call to the backend with the PATCH update ("approval" in this case)
        // Depending on the response, button changes the Detail Display title text to inform the manager of 
        // the outcome of the approval submission.  Custom "pause" function waitfor pauses
        //  execution for a few seconds, allowing the changed text to linger a bit, then 
        //  returns to the original text
        let accResponse = await assessRequest(1);
        let text = document.getElementById("detail-panel-title-text");
        let tmp = text.innerText;
        if(accResponse.message == "Approved Request."){
            text.innerText ='Approval Logged';
            text.style.color = "green";
            await waitfor(4000);
            text.innerText = tmp;
            text.style.color = "black";
        }else{
            text.innerText ="Error logging approval"
            await waitfor(3000);
            text.innerText = tmp;
        }
    });
    let rejectButton = document.createElement("button");
    rejectButton.id = "item-reject-btn";
    rejectButton.className = "assess-btn";
    rejectButton.classList.add("btn", "btn-danger");
    rejectButton.innerText = "Reject";
    rejectButton.addEventListener("click", async (event) => {
        // just like the accept button, save for the request code and the display
        let rejResponse = await assessRequest(0);
        let text = document.getElementById("detail-panel-title-text");
        let tmp = text.innerText;
        if(rejResponse.message == "Request Rejected."){
            text.innerText ='Approval Rejected';
            text.style.color = "red";
            await waitfor(4000);
            text.innerText = tmp;
            text.style.color = "black";
        }else{
            text.innerText ="Error logging rejection"
            await waitfor(3000);
            text.innerText = tmp;
        }
    });
    assessButtonContainer.append(acceptButton, rejectButton);
    detailPanel.appendChild(assessButtonContainer);
    // inner detail panel headers and data divs
    // headersArr used to populate the detail panel category headers
    var headersArr = ["ID", "Employee", "Amount", "Submit Time", "Description", "Type", "Status"];
    for(i=0;i<headersArr.length;i++){
        let headerItem = document.createElement("li");
        headerItem.className = "table-data";
        headerItem.classList.add("table-headers");
        headerItem.innerText = headersArr[i];
        let dataItem = document.createElement("li");
        dataItem.className = "table-data";
        dataItem.classList.add("table-data-text");
        headersListElem.appendChild(headerItem);
        dataListElem.appendChild(dataItem);
    }
    // This function populates the list of items on the right panel
    // The header is hard-coded in HTML/CSS
    requestLister(requests);
    // TARGET PARENT: controlPanel
    // main button container contains the all of the buttons for filtering results
    let buttonContainerElem = document.createElement("div");
    buttonContainerElem.className = "button-container";
    // InnerText and identifiers for the 4 button types
    let buttonTxt = ["Pending", "Approved", "Denied", "All"];
    let btnID = ["btn-pending", "btn-approved", "btn-rejected", "btn-all"];
    // loop to make all buttons according to their details, and append them to the main container
    for(i=0;i<buttonTxt.length;i++){
        let buttonElem = document.createElement("button");
        buttonElem.className = "control-buttons";
        buttonElem.id = btnID[i];
        buttonElem.innerText = buttonTxt[i];
        buttonContainerElem.appendChild(buttonElem);
    }
    controlPanel.appendChild(buttonContainerElem);
    // FILTER BUTTON CLICK FUNCTIONALITY
    // Calls functions for redrawing the items list acc to their type
    let pendingButton = document.getElementById("btn-pending");
    pendingButton.addEventListener("click", () => {
        displayPending();
    });
    let approvedButton = document.getElementById("btn-approved");
    approvedButton.addEventListener("click",() => {
        displayApproved();
    });
    let rejectedButton = document.getElementById("btn-rejected");
    rejectedButton.addEventListener("click", () => {
        displayRejected();
    });
    let allButton = document.getElementById("btn-all");
    allButton.addEventListener("click", () => {
        displayAll();
    });
    // APPEND
    overallDisplay.appendChild(detailPanel);
    overallDisplay.appendChild(totalItemContainer);
}

// List Manipulation functions for Control Panel buttons
function clearRequestItems(){
    let items = Array.from(document.getElementsByClassName("item"));
    items.forEach(item => {
        item.remove();
    });
}
function displayPending(){
    let pendingSet = parsePendingFromRequests();
    clearRequestItems();
    requestLister(pendingSet);
}
function displayApproved(){
    let approvedSet = parseApprovedFromRequests();
    clearRequestItems();
    requestLister(approvedSet);
}
function displayRejected(){
    let rejectedSet = parseRejectedFromRequests();
    clearRequestItems();
    requestLister(rejectedSet);
}
function displayAll(){
    clearRequestItems();
    requestLister(requests);
}

// LOGOUT BUTTON FUNCTIONALITY
let logoutButton = document.getElementById("logout-button");
console.log(logoutButton);
logoutButton.type = "button";
logoutButton.addEventListener("click", async () => {
    console.log("logout clicked")
    let response = await fetch("/session", {
        method: "DELETE"
    });
    let responseBody = await response.json();
    window.location = "../";
});