const bcrypt = require('bcryptjs');
const saltRounds = 10; 

async function encryptPasswords(users) {
    for (let user of users) {
        if (user.password) {
            const salt = await bcrypt.genSalt(saltRounds);
            const hashedPassword = await bcrypt.hash(user.password, salt);

            user.password = hashedPassword;
        }
    }
    return users;
}

module.exports = encryptPasswords;