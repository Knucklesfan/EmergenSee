function validateuser() {
    fetch("/verifyUser", {
        method: 'POST',
    })
    .then((response) => response.json())
    .then((json) => console.log(json));
}
function signin(totp) {
    fetch("/verifyUser?totp="+totp, {
        method: 'POST',
    })
    .then((response) => response.json())
    .then((json) => console.log(json));

}
