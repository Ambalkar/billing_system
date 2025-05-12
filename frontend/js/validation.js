function validateLoginForm() {
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();
    const errorMsg = document.getElementById('errorMsg');
    errorMsg.textContent = '';

    if (username === '' || password === '') {
        errorMsg.textContent = 'Username and password are required.';
        return false;
    }
    return true;
}

function validateBillForm() {
    const billDate = document.getElementById('billDate').value;
    const dueDate = document.getElementById('dueDate').value;
    const totalAmount = document.getElementById('totalAmount').value;
    const errorMsg = document.getElementById('errorMsg');
    errorMsg.textContent = '';

    if (!billDate || !dueDate) {
        errorMsg.textContent = 'Bill date and due date are required.';
        return false;
    }
    if (new Date(dueDate) < new Date(billDate)) {
        errorMsg.textContent = 'Due date cannot be before bill date.';
        return false;
    }
    if (totalAmount === '' || isNaN(totalAmount) || Number(totalAmount) <= 0) {
        errorMsg.textContent = 'Total amount must be a positive number.';
        return false;
    }
    return true;
}

function validateSettingsForm() {
    const fullName = document.getElementById('fullName').value.trim();
    const email = document.getElementById('email').value.trim();
    const errorMsg = document.getElementById('errorMsg');
    errorMsg.textContent = '';

    if (fullName === '' || email === '') {
        errorMsg.textContent = 'Full name and email are required.';
        return false;
    }
    const emailPattern = /^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$/;
    if (!emailPattern.test(email)) {
        errorMsg.textContent = 'Invalid email format.';
        return false;
    }
    return true;
}
