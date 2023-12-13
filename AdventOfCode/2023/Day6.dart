import 'dart:math';

final numExp = RegExp(r'\d+');

void main() {
  String test = '''
Time:      7  15   30
Distance:  9  40  200''';
  String input = '''
Time:        60     80     86     76
Distance:   601   1163   1559   1300
''';

  // tx - x^2 > d
  // max value of lhs is at x=t/2
  // solving for tx-x^2=d will give two values of x (min and max)
  // x = (t +/- sqrt(t^2 - 4d)) / 2

  var lines = input.replaceAll(' ', '').split('\n');

  final times =
      numExp.allMatches(lines[0]).map((e) => int.parse(e[0]!)).toList();
  final distances =
      numExp.allMatches(lines[1]).map((e) => int.parse(e[0]!)).toList();

  int res = 1;
  for (int i = 0; i < times.length; ++i) {
    int t = times[i];
    int d = distances[i];

    final tSqMinus4dRoot = sqrt(t * t - 4 * d);
    final x1 = ((t + tSqMinus4dRoot) / 2);
    final x2 = (t - tSqMinus4dRoot) / 2;
    print('t=$t, d=$d');
    print('x1=$x1, x2=$x2');
    if (x1 < x2) {
      final m1 = (x1 + 1).floor();
      final m2 = (x2 - 1).ceil();
      res *= m2 - m1 + 1;
    } else {
      final m1 = (x2 + 1).floor();
      final m2 = (x1 - 1).ceil();
      res *= m2 - m1 + 1;
    }
  }
  print(res);
}
