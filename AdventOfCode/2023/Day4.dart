void main() {
  final input = '''
Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11''';
  final cards = input.split('\n');
  final dp = List<int>.filled(cards.length, 1);
  int res = 0;
  cards.reversed.indexed.forEach((x) {
    final i = x.$1;
    final c = x.$2;
    final numbers = c.split(':')[1].split('|');
    final winning = numbers[0];
    final have = numbers[1];
    final regex = RegExp(r'(\d+)');
    final wSet = regex.allMatches(winning).map((m) => int.parse(m[0]!)).toSet();
    final hSet = regex.allMatches(have).map((m) => int.parse(m[0]!)).toSet();
    int score = wSet.intersection(hSet).length;
    //print('wSet=$wSet');
    //print('score=$score');
    for (int j = 0; j < score; ++j) {
      dp[i] += dp[i-j-1];
    }
    res += dp[i];
  });
  print(res);
}
