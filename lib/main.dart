import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Ship Time Widget',
      home: TimeWidget(),
    );
  }
}

class TimeWidget extends StatefulWidget {
  @override
  _TimeWidgetState createState() => _TimeWidgetState();
}

class _TimeWidgetState extends State<TimeWidget> {
  String _time = 'Loading...';

  @override
  void initState() {
    super.initState();
    _fetchTime();
  }

  // Fetch the time from the API
  Future<void> _fetchTime() async {
    try {
      final response = await http.get(Uri.parse('https://ship-tracker-and-time.vercel.app/api/ship_time?imo=9931109'));

      if (response.statusCode == 200) {
        // Parse the JSON response
        final data = json.decode(response.body);
        final localTime = data['local_time']; // Example: "2024-12-28 19:32:52 CST+0800"
        final time = localTime.split(' ')[1].substring(0, 5); // Extract "19:32"

        setState(() {
          _time = time; // Update the widget with the time
        });
      } else {
        setState(() {
          _time = 'Error fetching time';
        });
      }
    } catch (e) {
      setState(() {
        _time = 'Error fetching time';
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Ship Time')),
      body: Center(
        child: Text(
          'Current Time: $_time',
          style: TextStyle(fontSize: 30),
        ),
      ),
    );
  }
}
