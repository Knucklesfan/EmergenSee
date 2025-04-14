const { v4: uuidv4 } = require('uuid');

var tokenize = function() {
    return Math.random().toString(36).substr(2); // remove `0.`
};

function registerUser(res, db, IMEI, MEID, reregister) {
    let token = uuidv4();
    let time = Date.now();
    // console.log(token)
    console.log(IMEI,MEID);

    if(!reregister) {
        db.all("INSERT INTO USER VALUES (?, ?, ?, ?)",[IMEI, MEID, time, token],  function (err, rows) {
            if(err){ //if we have an error, just straight up die
            res.status(401).json({"success":"false","error":err});
            throw err
            }
            else{ //otherwise, lets see here a
                res.status(401).json({"success":"true","reregistered":"false"});
            }
        });
    }
    else { //otherwise, we gotta reregister, and register that the token has been updated
        db.all("UPDATE USER SET token = ?, MEID = ? WHERE IMEI = ?",[token, MEID, IMEI],  function (err, rows) {
            if(err){ //if we have an error, just straight up die
            res.status(401).json({"success":"false","error":err});
            throw err
            }
            else{ //otherwise, lets see here a
                db.all("INSERT INTO USER_REREGISTERED (IMEI, REGISTERED, TOKEN) VALUES (?, ?, ?)",[IMEI, time, token],  function (err, rows) {
                    if(err){ //if we have an error, just straight up die
                    res.status(401).json({"success":"false","error":err});
                    throw err
                    }
                    else{ //otherwise, lets see here a
                        res.status(401).json({"success":"true","reregistered":"true"});
                    }
                });
                    }
        });

    }
};

module.exports = {registerUser};
