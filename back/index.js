const express = require('express');

const path = require('path');
const app = express();
const port = 3000;
//this project used sqlite because i dont care
const sqlite3 = require('sqlite3').verbose()
const db = new sqlite3.Database('customers.sqlite');

db.serialize(() => {
    db.run(`CREATE TABLE IF NOT EXISTS USER (
      IMEI INTEGER PRIMARY KEY,
      MEID INTEGER,
      REGISTERED INTEGER,
      TOKEN TEXT
    );`)
    //logs every time the user reregisters, in case that another device spoofs an IMEI, we'll know lol
    db.run(`CREATE TABLE IF NOT EXISTS USER_REREGISTERED (
      ID INTEGER PRIMARY KEY AUTOINCREMENT,
        IMEI INTEGER,
        REGISTERED INTEGER,
        TOKEN TEXT,
        FOREIGN KEY(IMEI) REFERENCES USER(IMEI)

      );`)
  
    db.run(`CREATE TABLE IF NOT EXISTS USER_REPORTED (
      ID INTEGER PRIMARY KEY AUTOINCREMENT,
      REGISTEREDIMEI INTEGER,
      REPORTID INTEGER,
      FOREIGN KEY(REGISTEREDIMEI) REFERENCES USER(IMEI),
      FOREIGN KEY(REPORTID) REFERENCES REPORT(ID)

    );`)
    db.run(`CREATE TABLE IF NOT EXISTS REPORT (
        ID INTEGER PRIMARY KEY AUTOINCREMENT,
        REGISTERED INTEGER,
        TYPE TEXT,
        DESC TEXT,
        FLAGS INTEGER,
        LATITUDE INTEGER NOT NULL,
        LONGTITUDE INTEGER NOT NULL
    );`)

  })
  
app.get('/register', (req, res) => { //parameters: register(MEID, IMEI) returns TOKEN
    db.all("SELECT IMEI FROM USER WHERE IMEI = ?",req.params['IMEI'], function (err, rows) {
        if(err){ //if we have an error, just straight up die
          res.status(401).json({"success":"false","error":"access denied."});
          }
          else{ //otherwise, lets see here
            registerUser(res, db,req.params['IMEI'],req.params['MEID'],rows.length > 0)
          }
      });
});

app.get('/alert', (req, res) => {
    db.all("SELECT TOKEN FROM USER WHERE TOKEN = ?",req.params['token'], function (err, rows) {
        if(err || rows.length <= 0){
          res.status(401).json({"success":"false","error":"access denied."});
          }
          else{
              res.status(400).json({"success":"true"});
          }
      });
  });

app.use(express.static('static'));
app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
});
