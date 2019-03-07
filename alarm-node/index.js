const request=require('request');


let url = 'http://localhost:8090/index';

request(url, function (err, response, body) {
  if(err){
    console.log('error:', error);
  } else {
    let alarms = JSON.parse(body)
    for( var alarm of alarms){
      console.log('Alarm :'+ alarm.id + ' - '+ alarm.text);
    }

  }
});
