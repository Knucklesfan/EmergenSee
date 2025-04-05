import { v4 as uuidv4 } from 'uuid';

var tokenize = function() {
    return Math.random().toString(36).substr(2); // remove `0.`
};

function registerUser(res, db, IMEI, MEID, reregister) {
    let token = uuidv4();
    if(!reregister) {
        db.all("INSERT INTO USERS VALUES (?, ?, ?, ?)",IMEI, MEID, new Date(), token,  function (err, rows) {
            if(err){ //if we have an error, just straight up die
            res.status(401).json({"success":"false","error":"error."});
            }
            else{ //otherwise, lets see here a
                res.status(401).json({"success":"true","error":"no error."});
            }
        });
    }
};
