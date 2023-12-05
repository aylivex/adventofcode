#!perl
use warnings;
use strict;

# Number of cubes in the bag
my %bag = (
    'red' => 12,
    'green' => 13,
    'blue' => 14
);

my $gameIdSum = 0;


open(INPUT, '<', "2023/input/day02.txt")
    or die $!;

while (<INPUT>) {
    (my $gameId) = /Game (\d+):/;

    # Remove the game id;
    s/.*: //;

    my $possible = 1;
    foreach my $set (split(/; ?/)) {
        my %game = ('red' => 0, 'green' => 0, 'blue' => 0);
        foreach (split(/, ?/, $set)) {
            (my $number, my $colour) = /(\d+) (\S+)/;
            $game{$colour} += $number;
        }

        if ($game{'red'} > $bag{'red'}
            || $game{'green'} > $bag{'green'}
            || $game{'blue'} > $bag{'blue'}) {
            $possible = 0;
            last;
        }
    }

    $gameIdSum += $gameId if $possible;
}

print "$gameIdSum\n";
