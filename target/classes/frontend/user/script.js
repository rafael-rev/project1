// 2. set up needed globals
let user;
let requests;
let displayContainer = document.getElementById("display-container");

// 3. set up the window load to check for session. If no session redir to login page
//      else, save the user and requests to the globals
window.onload = async () => {
    let response = await fetch("http://localhost:8999/session");
    let responseBody = await response.json();
    if(!responseBody.success){
        console.log("Session not found");
        // redir to login
        //window.location("../");
    }else{
        user = responseBody.data;
        requests = await getUserRequests();
    }
    console.log(requests);
    // displayRequests();
    // let itemCollection = document.getElementsByClassName("item-detail");
    //     console.log(itemCollection);
}
// 4
async function getUserRequests(){
    let response = await fetch("http://localhost:8999/api/user");
    let responseBody = await response.json();
    return responseBody.data;
}

// let itemsDict = {
//     "item-id": "Item1",
//     "item-value": "$700.00",
//     "item-submitted": "2022-08-02",
//     "item-description": "Prostitutes",
//     "item-type": "item-detail",
//     "item-status": "pending"
// }

let itemsArray = ["item-id", "item-value", "item-submitted", "item-description", "item-type", "item-status"];

let detailKeyArray = ["id", "amount", "time_submitted", "description", "type_id", "status_id"];

function elementCreator(tag, properties){
    // assign copies given properties to the element made from the tag, then returns the element
    return Object.assign(document.createElement(tag), (properties || {}));
}

function childAppender(parent, child){
    // appends a child to a parent and returns the parent
    // for recursive adding for logical hierarchy
    if(child){
        parent.appendChild(child);
    }return parent;
}

function displayRequests(){
    requests.forEach(request => {
        let dispContainerElem = document.createElement("div");
        dispContainerElem.id = "display-container";

        let itemContainerElem = document.createElement("div");
        itemContainerElem.className = "item";

        // for(i = 0; i <= itemsArray.length; i++){
        //     let itemDetailElem = document.createElement("div");
        //     itemDetailElem.className = "item-detail";
        //     itemDetailElem.id = itemsArray[i];
        //     itemDetailElem.innerText = request.
        // }
        
        


        let controlPanelElem = document.createElement("div");
        controlPanelElem.className = "control-panel";

        let buttonContainerElem = document.createElement("div");
        buttonContainerElem.className = "button-container";

        let ctrlpanelButtonElem = document.createElement("button");
        ctrlpanelButtonElem.className = "control-buttons";

        dispContainerElem.appendChild(itemContainerElem);
        itemContainerElem.appendChild(itemDetailElem);

        controlPanelElem.appendChild(buttonContainerElem);
        buttonContainerElem.appendChild(ctrlpanelButtonElem);
    })
}