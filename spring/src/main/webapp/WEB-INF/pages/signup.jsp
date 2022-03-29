<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: koval-b
  Date: 10.02.22
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <style>
        <%@ include file="/WEB-INF/styles/add_edit_user.css" %>
    </style>
    <script>
        let checkPassword = function () {
            const message = document.getElementById('message');
            if (document.getElementById('password').value === document.getElementById('confirm_password').value
                && document.getElementById('password').value !== "") {

                message.style.color = 'green';
                message.innerHTML = 'matching';
                return true;
            } else {
                message.style.color = 'red';
                message.innerHTML = 'not matching';
                return false;
            }
        };

        let checkInput = function () {
            const button = document.getElementById('okButton');
            button.disabled = !(checkPassword()
                && !(document.getElementById('password').value === ""
                    || document.getElementById('login').value === ""
                    || document.getElementById('lastName').value === ""
                    || document.getElementById('firstName').value === ""
                    || document.getElementById('email').value === ""))
        }
    </script>
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>

</head>
<body onload="checkInput()">

<form method="post" action="${pageContext.request.contextPath}/signup" modelAttribute="userToAdd">
    <div class="title">Add User</div>
    <div class="subtitle">Let's create new userEntity!</div>

    <c:if test="${errorMessage ne null}">
        <div class="alert-captcha-error" id="error">${errorMessage}</div>
    </c:if>

    <div class="input-container ic1">
        <input id="login" class="input" type="text" placeholder=" "
               pattern="[a-z]{1,15}"
               title="Username should only contain lowercase letters." name="login" onkeyup='checkInput();' required/>
        <div class="cut">
            <div class="cutLogin"></div>
        </div>
        <label for="login" class="placeholder">Login</label>
    </div>

    <div class="input-container ic2">
        <input id="password" class="input" type="password" placeholder=" " name="password" onkeyup='checkInput();'
               required/>
        <div class="cut">
            <div class="cutPassword"></div>
        </div>
        <label for="password" class="placeholder">Password</label>
    </div>

    <div class="input-container ic2">
        <input id="confirm_password" class="input" type="password" name="confirm_password" onkeyup='checkInput();'
               placeholder=" " required/>
        <div class="cut">
            <div class="cutConfirmPassword"></div>
        </div>
        <label for="password" class="placeholder">Confirm password</label>
        <span id='message' style="float:right;"></span>
    </div>

    <div class="input-container ic2">
        <input id="email" class="input" type="email" placeholder=" " name="email" onkeyup='checkInput();' required/>
        <div class="cut">
            <div class="cutEmail"></div>
        </div>
        <label for="email" class="placeholder">Email</label>
    </div>

    <div class="input-container ic2">
        <input id="firstName" class="input" type="text" placeholder=" "
               pattern="[a-zA-Z]{2,15}" title="Only letters between 2 and 15" name="firstName" onkeyup='checkInput();'
               required/>
        <div class="cut">
            <div class="cutFirstName"></div>
        </div>
        <label for="firstname" class="placeholder">First name</label>
    </div>

    <div class="input-container ic2">
        <input id="lastName" name="lastName" class="input" type="text" placeholder=" "
               pattern="[a-zA-Z]{2,15}" title="Only letters between 2 and 15" onkeyup='checkInput();' required/>
        <div class="cut">
            <div class="cutLastName"></div>
        </div>
        <label for="lastname" class="placeholder">Last name</label>
    </div>

    <div class="input-container ic2">
        <input id="birthday" name="birthday" class="input" type="date" placeholder=" "
               value="2022-01-01" min="1930-01-01" max="2022-12-31" required/>
    </div>

    <div class="g-recaptcha" data-sitekey="6Lez4OseAAAAADkygDlIVCf0S3RX64C2eXwYsomv"></div>

    <input type="submit" value="ok" class="submit" id="okButton"/>
    <input type="button" value="cancel" class="cancel" onclick="document.location.href='/admin/main_page'">
</form>
</body>
</html>