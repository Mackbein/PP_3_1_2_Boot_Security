function getAll() {
    const url = 'http://localhost:8080/api';
    const container = $('#tbody');
    $.ajax({
        url: url,
        dataType: 'json',
        type: "GET",
    }).done((response) => {

        let html = '';

        response.forEach((item) => {
            let trHtml =
                `<tr data-id="${item.id}">
                    <td>${item.id}</td>
                    <td>${item.firstName}</td>
                    <td>${item.lastName}</td>
                    <td>${item.age}</td>
                    <td>${item.email}</td>
                    <td>${item.roles}</td>
                    <td><a id="buttonEditUser" role="button" onclick="getEditModal(${item.id})" class="btn btn-primary btn-sm" data-target="#buttonEditUser">Edit</a></td>
                    <td><a id="buttonDeleteUser" role="button" onclick="getDeleteModal(${item.id})" class="btn btn-danger btn-sm" >Delete</a></td>
                </tr>`;
            html += trHtml;
        })

        container.html(html);
    })
}

function getEditModal(id) {
    $.ajax({
        url: 'http://localhost:8080/api/' + id + '/show',
        dataType: 'json',
        type: 'GET'
    }).done(user => {
        let html = document.getElementById("modalEdit");
        let roles = ['ROLE_ADMIN', 'ROLE_USER'];

        html.innerHTML = `<div id="edit" class="modal fade" tabIndex="-1" role="dialog" 
                              aria-labelledby="TitleModalLabel" aria-hidden="true"
                              data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Edit user</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">?</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="formEditUser" 
                              class="form-signin mx-auto font-weight-bold text-center"
                              style="width: 200px;">
                            <p>
                                <label for="del_id">ID</label>
                                <input class="form-control form-control-sm" type="text" name="id" id="del_id"
                                       readOnly value="${user.id}">
                            </p>
                                <input type="text" name="firstName" value="${user.firstName}" id="firstName"></p>
                            <p><label for="lastName">Last Name</label>
                                <input type="text" name="lastName" value="${user.lastName}" id="lastName"></p>
                            <p><label for="Password">Password</label>
                                <input type="password" name="password" value="${user.password}" id="Password"></p>
                            <p><label for="age">Age</label>
                                <input type="number" name="age" value="${user.age}" id="age"></p>
                            <p><label for="email">Email</label>
                                <input type="text" name="email" value="${user.email}" id="email"></p>
                            <p>
                            <label>Role</label>
                                <select id="roles" multiple size="2" required
                                               class="form-control form-control-sm">
                                        <option value="ROLE_ADMIN">ADMIN</option>
                                        <option value="ROLE_USER" selected>USER</option>
                                </select>
                            </p>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                                    </button>
                                    <button type="button" 
                                            class="btn btn-primary js-edit-user">Edit
                                    </button>
                                </div>
                                </form>
                    </div>
                </div>
            </div>
        </div>`
        $('#edit').modal();
        $('.js-edit-user').click(function () {
            editUser(user);
        })
    })

    function editUser(user) {
        const form = document.getElementById('formEditUser');
        const formData = new FormData(form);
        const data = {
            roles: $('#roles').val()
        };
        console.log(data)
        for (let key of formData.keys()) {
            data[key] = formData.get(key);
        }

        $.ajax({
                url: 'http://localhost:8080/api/update/',
                type: 'PUT',
                contentType: 'application/json',
                processData: false,
                cash: false,
                data: JSON.stringify(data)
            }
        ).always(function () {
            getUser(id);
            $('#edit').modal('hide');
        })
    }
}

$(document).ready(() => {
    getAll();
})

function getDeleteModal(id) {
    $.ajax({
        url: 'http://localhost:8080/api/' + id + '/show',
        dataType: 'json',
        type: 'GET'
    }).done(user => {
        let adminSelect = "";
        let userSelect = "";

        let html = document.getElementById("modalEdit");
        html.innerHTML = `<div id="edit" class="modal fade" tabIndex="-1" role="dialog" 
                              aria-labelledby="TitleModalLabel" aria-hidden="true"
                              data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Delete user</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">?</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="formDeleteUser" 
                              class="form-signin mx-auto font-weight-bold text-center"
                              style="width: 200px;">
                            <p>
                                <label for="del_id">ID</label>
                                <input class="form-control form-control-sm" type="text" name="id" id="del_id"
                                       readOnly value="${user.id}">
                            </p>
                            <p><label for="firstName">First Name</label>
                                <input type="text" name="firstName" readonly value="${user.firstName}" id="firstName"></p>
                            <p><label for="lastName">Last Name</label>
                                <input type="text" name="lastName" readonly value="${user.lastName}" id="lastName"></p>
                            <p><label for="Password">Password</label>
                                <input type="password" name="password" readOnly value="${user.password}" id="Password"></p>
                            <p><label for="age">Age</label>
                                <input type="number" name="age" readOnly value="${user.age}" id="age"></p>
                            <p><label for="email">Email</label>
                                <input type="text" name="email" readOnly value="${user.email}" id="email"></p>
                            <br>
                            <p>
                            <label>Role</label>
                                <select id="editRoles" name="roles" multiple size="2" required
                                               class="form-control form-control-sm">
                                        <option value="${user.roles}">ADMIN</option>
                                        <option value="${user.roles}">USER</option>
                                </select>
                            </p>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                                    </button>
                                    <button type="button" 
                                            class="btn btn-danger js-delete-user">Delete
                                    </button>
                                </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>`
        $('#edit').modal();
        $('.js-delete-user').click(function () {
            deleteUser();
        })
    })

    function deleteUser() {
        const form = document.getElementById('formDeleteUser');
        const formData = new FormData(form);
        const data = {};

        for (let key of formData.keys()) {
            data[key] = formData.get(key);
        }
        const id = data.id

        $.ajax({
                url: 'http://localhost:8080/api/delete/' + id + '',
                type: 'DELETE',
                contentType: 'application/json',
                processData: false,
                cash: false,
                data: JSON.stringify(data)
            }
        ).always(function () {
            $(`tr[data-id=${data.id}]`).remove()
            $('#edit').modal('hide');
        })
    }
}

function addUser() {
    let user = {
        firstName: $('#new_user #firstName').val(),
        lastName: $('#new_user #lastName').val(),
        age: $('#new_user #age').val(),
        email: $('#new_user #email').val(),
        password: $('#new_user #password').val(),
        roles: $('#addroleList').val()
    }

    $.ajax({
            url: 'http://localhost:8080/api/create',
            type: 'POST',
            contentType: 'application/json',
            processData: false,
            cash: false,
            data: JSON.stringify(user)
        }
    ).done(function () {
        getAll();
        $('.ustable').click()
    })
}

function getUser(id) {
    const url = 'http://localhost:8080/api/' + id + '/show';
    const container = $('#tbody');
    $.ajax({
        url: url,
        dataType: 'json',
        type: "GET",
    }).done((response) => {
        let item = response;

        let trHtml =
            `
                    <td>${item.id}</td>
                    <td>${item.firstName}</td>
                    <td>${item.lastName}</td>
                    <td>${item.age}</td>
                    <td>${item.email}</td>
                    <td>${item.roles}</td>
                    <td><a id="buttonEditUser" role="button" onclick="getEditModal(${item.id})" class="btn btn-primary btn-sm" data-target="#buttonEditUser">Edit</a></td>
                    <td><a id="buttonDeleteUser" role="button" onclick="getDeleteModal(${item.id})" class="btn btn-danger btn-sm" >Delete</a></td>
                `;
        container.find(`tr[data-id=${item.id}]`).html(trHtml)
    })
}
