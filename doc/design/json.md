### [2016/04/06]10:55AM

![p](json.png)
![p](hierarchy.png)

### [2016/04/05]06:39PM

The JSON parser we use is very simple, so the JSON source should follow the rules.

1. JSON file starts with `{` and ends with `}`.
1. Each line should contain only one element.
1. Only String, Number (Integer and Floating point), String, and a List is allowed.
1. The string data should be enclosed in `""`.

This is an example of well-behave JSON file.

    {
        "string": "James",
        "age": 10,
        "date": [10, 3, 17],
        "time": [12, 14],
        "lattitude": [1, 2, 3, 4],
        "longitude": [11, 12, 13, 14]
    }