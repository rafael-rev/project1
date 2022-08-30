// 2. set up needed globals
let user;
let requests;
let displayContainer = document.getElementById("display-container");
let controlPanel = document.getElementById("control-panel");

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
    console.log(requests);
    requests.forEach((request, id) => {
        console.log("REQUEST#"+id+": ");
        console.log(request);
    })
    displayRequests();
    // let itemCollection = document.getElementsByClassName("item-detail");
    //     console.log(itemCollection);
}
// 4
async function getUserRequests(){
    let response = await fetch("http://localhost:8999/api/user");
    let responseBody = await response.json();
    return responseBody.data;
}

let itemsArray = ["item-id", "item-value", "item-submitted", "item-description", "item-type", "item-status"];

let detailKeyArray = ["id", "amount", "time_submitted", "description", "type", "status"];

function displayRequests(){
    requests.forEach(request => {
        // TARGET: displayContainer

        // Single ITEM inside the displayContainer
        let itemContainerElem = document.createElement("div");
        itemContainerElem.className = "item";

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
                itemDetailElem.style.color = "red";
            }else if(request[detailKeyArray[i]]=="approved"){
                itemDetailElem.style.color = "green";
            }
            itemContainerElem.appendChild(itemDetailElem);
        }
        // After adding the details, append the item to the displayContainer
        displayContainer.appendChild(itemContainerElem);
    });
    // TARGET: controlPanel
    // making the buttons
    //  only 2 buttons, so 
    let buttonContainerElem = document.createElement("div");
    buttonContainerElem.className = "button-container";

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