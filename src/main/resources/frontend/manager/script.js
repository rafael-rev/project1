// 2. set up needed globals
let user;
let requests;
let totalItemContainer = document.getElementById("item-container"); //contains items
let controlPanel = document.getElementById("control-panel"); // contains control buttons
let detailPanel = document.getElementById("detail-panel"); // contains details for a req
let overallDisplay = document.getElementById("main-display"); // contains totalItemContainer & detailPanel
let itemsArray = ["item-id", "item-author", "item-value", "item-submitted", "item-description", "item-type", "item-status"];

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
    console.log(requests);
    requests.forEach((request, id) => {
        console.log("REQUEST#"+id+": ");
        console.log(request);
    })
    displayPage(requests);
    getFullName(2);
    // let itemCollection = document.getElementsByClassName("item-detail");
    //     console.log(itemCollection);
}
// 4
async function getRequests(){
    let response = await fetch("http://localhost:8999/api/manager");
    let responseBody = await response.json();
    return responseBody.data;
}

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
        if(request["status"] === "rejected"){
            outputArr.push(request);
        }
    });
    return outputArr;
}

function requestLister(requests){
    let detailKeyArray = ["id", "author", "amount", "time_submitted", "description", "type", "status"];

    requests.forEach(request => {
        // TARGET: totalItemContainer
        // Single ITEM inside the displayContainer
        let itemElem = document.createElement("div");
        itemElem.className = "item";

        // The 6 details to populate the ITEM
        for(i = 0; i < itemsArray.length; i++){
            //console.log("ITEM DETAIL ITER "+i);
            let itemDetailElem = document.createElement("div");
            itemDetailElem.className = "item-detail";
            //console.log("ITEM ID: "+itemsArray[i]);
            itemDetailElem.id = itemsArray[i];
            //console.log("DETAIL: "+detailKeyArray[i]);
            
            // Setting the details for each of the 6;
            // color coding pending/approved status
            itemDetailElem.innerText = request[detailKeyArray[i]];
            if(request[detailKeyArray[i]]=="pending"){
                itemDetailElem.style.color = "yellow";
            }
            itemElem.appendChild(itemDetailElem);
        }
        itemElem.addEventListener("click", async (event) => {
                event.stopPropagation();
                let target = event.currentTarget;
                //console.log(target.querySelector("div").className)
                // Gets the userID from the Item
                let itemAuthorDiv = target.querySelectorAll("#item-author");
                console.log(itemAuthorDiv[0].innerText); 
                let targetUserID = itemAuthorDiv[0].innerText;
                // Gets the selected Item's ID
                let displayData = target.querySelectorAll("#item-id");
                let targetItemID = displayData[0].innerText;
                let targetItem;  // var to store the full Item that matches the condition by ID#
                requests.forEach(request =>{
                    if(request["id"] == targetItemID){
                        targetItem = request;
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

function displayDetailsForReq(id, author, value, submitted, description, type, status){
    let detailPanelEntry = document.getElementById("detail-panel-text");
}

async function getFullName(id){
    let response = await fetch(`/api/manager/${id}`, {
        method: "GET"
    });
    let responseBody = await response.text();
    return responseBody;
}

function displayPage(){
    // let detailPanel = document.createElement("div");
    // detailPanel.id = "detail-panel";
    let detailPanelTitleContainer = document.createElement("div");
    detailPanelTitleContainer.id = "detail-panel-title";
    detailPanel.appendChild(detailPanelTitleContainer);
    let detailPanelTitle_Text = document.createElement("h3");
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
    let headersListElem = document.createElement("ul");
    headersListElem.className = "listbox";
    let dataListElem = document.createElement("ul");
    dataListElem.className = "listbox";
    detailPanelTextHeaders.append(headersListElem);
    detailPanelData.append(dataListElem);
    let headersArr = ["ID", "Employee", "Amount", "Submit Time", "Description", "Type", "Status"];
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
    requestLister(requests);
    // TARGET: controlPanel
    // making the buttons
    //  only 2 buttons, so 
    let buttonContainerElem = document.createElement("div");
    buttonContainerElem.className = "button-container";

    let buttonTxt = ["Pending", "Approved", "Rejected", "All"];
    let btnID = ["btn-pending", "btn-approved", "btn-rejected", "btn-all"];
    for(i=0;i<buttonTxt.length;i++){
        let buttonElem = document.createElement("button");
        buttonElem.className = "control-buttons";
        buttonElem.id = btnID[i];
        buttonElem.innerText = buttonTxt[i];
        buttonContainerElem.appendChild(buttonElem);
    }
    controlPanel.appendChild(buttonContainerElem);

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

function getOneForDisplayPanel(){
    // use a filter by ID func
    // parse that request/item into a list
    // 
}

function assessRequest(decision){
    // have "current request" already set by click
    if(decision){
        // toggle pending to approved for the request
    }else{
        // toggle rejected, color request status text red
    }
}
