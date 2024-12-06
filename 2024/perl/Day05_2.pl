#!perl
use warnings;
use strict;

open(INPUT, '<', "input.txt")
    or die $!;

my %reverse;
while (<INPUT>) {
    last if /^$/;
    (my $left, my $right) = /(\d+)\|(\d+)/;
    if (defined $reverse{$right}) {
        push @{$reverse{$right}}, $left;
    } else {
        $reverse{$right} = [ $left ];
    }
}

my @pages;
while (<INPUT>) {
    chop;
    my @page_list = split /,/;
    push @pages, \@page_list;
}

my $middle_sum = 0;

for my $update (@pages) {
    my @update_pages = @{$update};
    
    my $corrected = 0;

    my $valid;
    do {
        $valid = 1;

        my $i = $#update_pages;
        while ($i > 0 && $valid) {
            my $left = $update_pages[$i];
            my $j = $i - 1;
            while ($j >= 0 && $valid) {
                my $right = $update_pages[$j];
                if ($i > 0 && defined $reverse{$right}) {
                    for my $page (@{$reverse{$right}}) {
                        if ($page == $left) {
                            $update_pages[$i] = $right;
                            $update_pages[$j] = $left;
                            $valid = 0;
                            $corrected++;
                            last;
                        }
                    }
                }
                --$j;
            }
            --$i;
        }
    } while (!$valid);

    if ($corrected > 0) {
        my $middle = $update_pages[($#update_pages + 1) / 2];
        $middle_sum += $middle;
    }
}

print "Answer: $middle_sum\n";
