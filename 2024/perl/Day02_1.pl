#!perl
use warnings;
use strict;

open(INPUT, '<', "2024/input/sample.txt")
    or die $!;

my $safe = 0;

input:
while (<INPUT>) {
    my @levels = split /\s+/;
    print "@levels\n";

    my $dir = 0;
    my $i = 0;
    do {{
        my $diff = $levels[$i] - $levels[$i + 1];
        print "\t$diff ($dir)\n";
        if ($diff == 0) {
            print "\t\t0\n";
            next input;
        }
        if (0 < $diff && $diff <= 3) {
            print "\t\t 3\n";
            next input if ($dir < 0);
            print "\t\t dir = 1\n";
            $dir = 1;
        } elsif (-3 <= $diff && $diff < 0) {
            print "\t\t-3\n";
            next input if ($dir > 0);
            print "\t\t dir = -1\n";
            $dir = -1;
        } else {
            next input;
        }
        print "\t\t$dir\n";
    }} while (++$i < $#levels - 1);

    print "\tsafe level\n";
    $safe++;
}

print "Safe: $safe\n";
