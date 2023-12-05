#!perl
use warnings;
use strict;

use List::Util qw( max );

my $power = 0;


open(INPUT, '<', "2023/input/day02.txt")
    or die $!;

while (<INPUT>) {
    # Remove the game id;
    s/.*: //;

    my %game = ('red' => 0, 'green' => 0, 'blue' => 0);
    foreach my $set (split(/; ?/)) {
        foreach (split(/, ?/, $set)) {
            (my $number, my $colour) = /(\d+) (\S+)/;
            $game{$colour} = max($game{$colour}, $number);
        }
    }

    $power += $game{'red'} * $game{'green'} * $game{'blue'};
}

print "$power\n";
