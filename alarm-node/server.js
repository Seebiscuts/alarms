const express = require('express')
const bodyParser = require('body-parser');
const request = require('request');

const app = express()
const port = 3030

app.use(express.static('public'));
app.use(bodyParser.urlencoded({ extended: true }));
app.set('view engine', 'ejs')
// app.use(bodyParser.urlencoded({ extended: true }));
app.get('/', function (req, res) {
  res.render('welcome');
})

app.get('/index', function(req, res){
  let url = 'http://localhost:8090/index';
  request(url, function (err, response, body) {
    if(err){
      res.render('index', {alarms: null, error: 'Error, please try again'});
    } else {
      let alarmsResponse = JSON.parse(body)
      let alarmDetails = [];
      for( var alarm of alarmsResponse){
        alarmDetails.push(alarm);
      }
      res.render('index', {alarms: alarmDetails, error:null});
    }
  });
});

app.listen(port, () => console.log(`Example app listening on port ${port}!`))
