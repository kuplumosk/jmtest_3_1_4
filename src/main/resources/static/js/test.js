var buttonLoad;
var dropdwonCountry;
var buttonAdd;
var fieldCountryName;

$(document).ready(function () {
    buttonLoad = $('#buttonLoadCountries');
    dropdwonCountry = $('#dropDownCountries');
    fieldCountryName = $('#fieldCountryName');
    buttonAdd = $('#buttonAdd');

    buttonLoad.click(function () {
        showAllUsers();
    });

    buttonAdd.click(function () {
        addNewUser();
    });

});

function showAllUsers() {
    let url = "/api/users";

    $.get(url, function (responseJSon) {
        dropdwonCountry.empty();

        $.each(responseJSon, function (index, user) {
            $('<option>').val(user.id).text(
                user.firstName + ' ' + user.lastName + ' ' + user.age + ' ' + user.username).appendTo(dropdwonCountry);
        })
    }).done(function () {
    }).fail(function () {
    })
}

function addNewUser() {
    let url = "/api/users";
    let countryName = fieldCountryName.val();
    let jsonData = {name: countryName};

    $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(jsonData),
        contentType: 'application/json'
    }).done(function (userId) {
        alert(userId);
    })
}

