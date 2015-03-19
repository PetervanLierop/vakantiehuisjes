/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function validateInlog(form)
{
    if (form.inlogName.value==""){
        alert("Uw naam in vullen a.u.b.");
        form.userName.focus();
    }
    else if (form.passWord.value==""){
        alert("Uw wachtwoord in vullen a.u.b.");
        form.passWord.focus();
    }
    else{
        form.submit();
    }
}

function validateChangePassword(form)
{
    if (form.oldPassword.value==""){
        alert("Uw oude wachtwoord in vullen a.u.b.");
        form.oldPassword.focus();
    }
    else if (form.newPassword.value==""){
        alert("Uw nieuwe wachtwoord in vullen a.u.b.");
        form.newPassword.focus();
    }
    else if (form.newPassword.value==form.checkNewPassword.value){
        form.submit(); 
    }
    else{
        alert("Controle Nieuw wachtwoord niet correct ingevoerd");
        form.checkNewPassword.focus();
    }
}

function validatePasswordForgotten(form)
{
    if (form.inLogName.value==""){
        alert("Uw naam in vullen a.u.b.");
        form.userName.focus();
    }
    else{
        form.submit();
    }
}

function validateAddUser(form)
{
    if (form.inlogName.value==""){
        alert("InlogNaam van de gebruiker invullen a.u.b.");
        form.inlogName.focus();
    }
    else if (form.firstName.value==""){
        alert("Voornaam van de gebruiker invullen a.u.b.");
        form.firstName.focus();
    }
    else if (form.lastName.value==""){
        alert("Achternaam van de gebruiker invullen a.u.b.");
        form.lastName.focus();
    }
    else if (form.emailAddress.value==""){
        alert("Email adres van de gebruiker invullen a.u.b.");
        form.emailAddress.focus();
    }
    else if (form.emailAddress.value.indexOf("@") == -1){
        alert("Email adres van de gebruiker is niet correct");
        form.emailAddress.focus();        
    }
    else{
        form.submit();
    }
}

function validateAddBookingDetails(form)
{
    if (form.startElectrReadingLow.value == "0" |
        form.startElectrReadingLow.value == ""){
        alert("Alle meterstanden invullen a.u.b.");
        form.startElectrReadingLow.focus();
    }
    else if (isNaN(form.startElectrReadingLow.value)){
        alert("Alleen numerieke waarde invullen.");
        form.startElectrReadingLow.focus();
    }
    else if (form.endElectrReadingLow.value <= form.startElectrReadingLow.value){
        alert("Eindstand is niet hoger dan de beginstand.");
        form.endElectrReadingLow.focus();
    }
    else if (isNaN(form.endElectrReadingLow.value)){
        alert("Alleen numerieke waarde invullen.");
        form.endElectrReadingLow.focus();
    }
    else if (form.startElectrReadingHigh.value == "0" |
        form.startElectrReadingHigh.value == ""){
        alert("Alle meterstanden invullen a.u.b.");
        form.startElectrReadingHigh.focus();
    }
    else if (isNaN(form.startElectrReadingHigh.value)){
        alert("Alleen numerieke waarde invullen.");
        form.startElectrReadingHigh.focus();
    }
    else if (form.endElectrReadingHigh.value <= form.startElectrReadingHigh.value){
        alert("Eindstand is niet hoger dan de beginstand.");
        form.endElectrReadingHigh.focus();
    }
    else if (isNaN(form.endElectrReadingHigh.value)){
        alert("Alleen numerieke waarde invullen.");
        form.endElectrReadingHigh.focus();
    }
    else{
        form.submit();
    }    
}

function validatePriceLevel(form)
{
    if (form.priceLevelDescription.value == ""){
        alert("Tariefnaam invullen a.u.b.");
        form.priceLevelDescription.focus();
    }
    else if (form.startWeekNumber.value == ""){
        alert("Startweeknumber invullen a.u.b.");
        form.startWeekNumber.focus();
    }
    else if (isNaN(form.startWeekNumber.value)){
        alert("Alleen numerieke waarde invullen.");
        form.startWeekNumber.focus();
    }
    else if (form.startWeekNumber.value < 1 |
        form.startWeekNumber.value > 53){
        alert("Weeknummer is niet geldig.");
        form.startWeekNumber.focus();
    }
    else if (form.endWeekNumber.value == ""){
        alert("Eindweeknumber invullen a.u.b.");
        form.endWeekNumber.focus();
    }
    else if (isNaN(form.endWeekNumber.value)){
        alert("Alleen numerieke waarde invullen.");
        form.endWeekNumber.focus();
    }
    else if (form.endWeekNumber.value < 1 |
        form.endWeekNumber.value > 53){
        alert("Weeknummer is niet geldig.");
        form.endWeekNumber.focus();
    }
    else if (form.priority.value == ""){
        alert("Prioriteit invullen a.u.b.");
        form.priority.focus();
    }
    else if (isNaN(form.priority.value)){
        alert("Alleen numerieke waarde invullen.");
        form.priority.focus();
    }
    else if (form.priority.value < 2){
        alert("Prioriteit moet groter dan 1 zijn.");
        form.priority.focus();
    }
    else{
        form.submit();
    }
}
