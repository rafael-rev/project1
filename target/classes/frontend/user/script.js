// 2. set up needed globals
let user;
let requests;
let displayContainer = document.getElementById("display-container"); //Main display div
let controlPanel = document.getElementById("control-panel");
const itemsArray = ["item-id", "item-value", "item-submitted", "item-description", "item-type", "item-status"];
const detailKeyArray = ["id", "amount", "time_submitted", "description", "type", "status"];

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
        requests = await getUserRequests();
    }
    displayRequests();
}
// FUNCTION TO GET ALL REIMB REQUESTS FOR THE LOGGED IN USER
async function getUserRequests(){
    let response = await fetch("http://localhost:8999/api/user");
    let responseBody = await response.json();
    return responseBody.data;
}

function displayRequests(){
    requests.forEach(request => {
        // TARGET: displayContainer
        // Single ITEM inside the displayContainer
        let itemContainerElem = document.createElement("div");
        itemContainerElem.className = "item";
        // The 6 details to populate the ITEM
        for(i = 0; i < itemsArray.length; i++){
            let itemDetailElem = document.createElement("div");
            itemDetailElem.className = "item-detail";
            itemDetailElem.id = itemsArray[i];  
            let key = detailKeyArray[i];
            let currentData = request[key];   
            console.log(`CURRENT TGT: ${itemsArray[i]}`)
            console.log(`CURRENT DATA: ${currentData}`) 
            // Formatting all "amount" number values to display decimal places
            if(itemsArray[i] == "item-value"){
                currentData = currentData.toFixed(2)
                console.log("INSIDE!!!!!!")
            }      
            // color coding pending/approved status
            itemDetailElem.innerText = currentData;
            if(currentData =="pending"){
                itemDetailElem.style.color = "orange";
            }else if(currentData =="approved"){
                itemDetailElem.style.color = "green";
                itemDetailElem.style.fontWeight = "bold";
            }
            itemContainerElem.appendChild(itemDetailElem);
        }
        // After adding the details, append the item to the displayContainer
        displayContainer.appendChild(itemContainerElem);
    });
    // TARGET PARENT: controlPanel
    // Container for the button
    let buttonContainerElem = document.createElement("div");
    buttonContainerElem.className = "button-container";
    // The button to Create New Request
    let ctrlpanelButtonElem = document.createElement("button");
    ctrlpanelButtonElem.className = "control-buttons";
    ctrlpanelButtonElem.innerText = "Create New Request"
    ctrlpanelButtonElem.addEventListener("click", () => {
        window.location = "../request";
    })
    // APPEND
    buttonContainerElem.appendChild(ctrlpanelButtonElem);
    controlPanel.appendChild(buttonContainerElem);
}

// LOGOUT BUTTON FUNCTIONALITY
let logoutButton = document.getElementById("logout-button");
logoutButton.type = "button";
logoutButton.addEventListener("click", async () => {
    // Kill session, then redirect to login page
    let response = await fetch("/session", {
        method: "DELETE"
    });
    let responseBody = await response.json();
    window.location = "../";
});