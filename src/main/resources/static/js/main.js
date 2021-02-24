const userList = document.g

('');














$('.table .eBtn').click(function (event) {
    event.preventDefault();
    var href = $(this).attr('href')
    $.get(href, function (user, status) {
        $('#formId').val(user.id);
        $('#formFirstName').val(user.firstName);
        $('#formLastName').val(user.lastName);
        $('#formAge').val(user.age);
        $('#formEmail').val(user.username);
        $('#formPassword').val(user.password);
    })
    $('#exampleModal').modal('show');
});

$('.table .dBtn').click(function (event) {
    event.preventDefault();
    var href = $(this).attr('href')
    $.get(href, function (user, status) {
        $('#deleteId').val(user.id);
        $('#deleteFirstName').val(user.firstName);
        $('#deleteLastName').val(user.lastName);
        $('#deleteAge').val(user.age);
        $('#deleteEmail').val(user.username);
        $('#deletePassword').val(user.password);
    })
    $('#deleteModal').modal('show');
});

$('.nav-item .nav-link').click(function () {
    $('.nav-link').addClass('text-primary');
    $(this).removeClass('text-primary');
});

$('.sBtn').click(function () {
    $('#filledForm').submit();
});

$('.dBtn').click(function () {
    $('#deleteForm').submit();
});
