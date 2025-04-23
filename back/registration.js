const { v4: uuidv4 } = require('uuid');
const admin = require('firebase-admin');

const serviceAccount = require('../fcmServiceAccountKey.json');

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
});

const messaging = admin.messaging();

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
            else{ //otherwise, lets see here aaaaa
                res.status(401).json({"success":"true","reregistered":"false","token":token,"error":"none."});
            }
        });
    }
    else { //otherwise, we gotta reregister, and register that the token has been updated
        db.all("UPDATE USER SET token = ?, MEID = ? WHERE IMEI = ?",[token, MEID, IMEI],  function (err, rows) {
            if(err){ //if we have an error, just straight up die
            res.status(401).json({"success":"false","error":err,"reregistered":"false","token":null});
            throw err
            }
            else{ //otherwise, lets see here a
                db.all("INSERT INTO USER_REREGISTERED (IMEI, REGISTERED, TOKEN) VALUES (?, ?, ?)",[IMEI, time, token],  function (err, rows) {
                    if(err){ //if we have an error, just straight up die
                        res.status(401).json({"success":"false","error":err,"reregistered":"false","token":null});
                    }
                    else{ //otherwise, lets see here a
                        res.status(401).json({"success":"true","reregistered":"true","token":token});
                    }
                });
                    }
        });

    }
};
function alertAll(res, db, token, lon, lat, type,imei) {
    let time = Date.now();

    db.all("INSERT INTO REPORT (REGISTERED, TYPE, DESC, FLAGS, LATITUDE, LONGTITUDE) VALUES (?, ?, ?, ?, ?, ?)",[time,type,"",0,lat,lon],  function (err, rows) {
        if(err){
            console.log("1")
            console.log(rows)
            res.status(401).json({"success":"false","error":"error submitting data."});
        }
        else{
            db.all("SELECT last_insert_rowid();",[],  function (err, rows) {
                if(err || rows.length <= 0){
                    console.log("2")

                    res.status(401).json({"success":"false","error":"error submitting data."});
                }
                else{
                    const lastid = rows["last_insert_rowid()"];
                    db.all("INSERT INTO USER_REPORTED (REGISTEREDIMEI, REPORTID) VALUES (?, ?)",[imei,lastid],  function (err, rows) {
                        if(err){
                            res.status(401).json({"success":"false","error":"error linking data."});
                        }
                        else{
                            //send out notif
                            const topicName = 'fcm_default_channel';

                            const message = {
                                notification: {
                                    title: "Emergency Nearby!",
                                    body: 'Emergency detected.'
                                },
                                "android":{
                                    "priority":"high"
                                },
                                topic: topicName,
                            };
                            console.log(messaging)
                            messaging.send(message)
                            .then((response) => {
                                // Response is a message ID string.
                                console.log('Successfully sent message:', response);
                                res.status(400).json({"success":"true","error":"success."});

                            })
                            .catch((error) => {
                                res.status(400).json({"success":"true","error":"failed sending alert."});
                            });
                        }

                    });
                }
            });

        }
    });


}

module.exports = {registerUser,alertAll};
