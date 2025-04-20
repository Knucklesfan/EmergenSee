const express = require('express');

const path = require('path');
const app = express();
const port = 3000;
//this project used sqlite because i dont care
const sqlite3 = require('sqlite3').verbose()
const db = new sqlite3.Database('customers.sqlite');
const registration = require('./registration.js');

db.serialize(() => {
    db.run(`CREATE TABLE IF NOT EXISTS USER (
      IMEI INTEGER PRIMARY KEY,
      FCMID TEXT,
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
//http://127.0.0.1:3000/register?imei=7b3d45eb-1292-4a43-9614-93afca0590aa&meid=35
app.get('/register', (req, res) => { //parameters: register(MEID, IMEI) returns TOKEN
    db.all("SELECT IMEI FROM USER WHERE IMEI = ?",req.query.imei, function (err, rows) {
        if(err || req.query.imei == undefined){ //if we have an error, just straight up die
          res.status(401).json({"success":"false","error":"access denied."});
          }
          else{ //otherwise, lets see here
            registration.registerUser(res, db,req.query.imei,req.query.meid,rows.length > 0)
          }
      });
});
//http://127.0.0.1:3000/alert?token="7b3d45eb-1292-4a43-9614-93afca0590aa"&lat=15&lon=16&type="stuff"
app.get('/alert', (req, res) => {
  console.log(req.query['token'])
  if(req.query['lon'] != undefined && req.query['lat'] != undefined && req.query['type'] != undefined && req.query['token'] != undefined) {
    db.all("SELECT IMEI FROM USER WHERE TOKEN = ?",req.query['token'], function (err, rows) {
      if(err || rows.length <= 0){

        res.status(401).json({"success":"false","error":"access denied."});
      }
      else{
        registration.alertAll(res,db, req.query['token'],req.query['lat'],req.query['lon'],req.query['type'],rows[0].IMEI)
      }
    });

  }
  else {
    res.status(401).json({"success":"false","error":"bad param."});
  }
  });

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
});
