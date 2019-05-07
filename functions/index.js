const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);


exports.pushNotification = functions.database.ref('/tips/{tipId}').onCreate((snapshot, context) => {

    //  Grab the current value of what was written to the Realtime Database.
    var valueObject = snapshot.val();


  // Create a notification
    const payload = {
        notification: {
            title:valueObject.title,
            body: valueObject.desc,
            sound: "default"
        },
    };

  //Create an options object that contains the time to live for the notification and the priority
    const options = {
        priority: "high",
        timeToLive: 60 * 60 * 24
    };


    return admin.messaging().sendToTopic("pushNotification", payload, options);
});