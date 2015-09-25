clear all; close all; clc
Brute_slopeTo = [680, 5456, 43680, 349504];
Brute_ratio = Brute_slopeTo(2:end) ./ Brute_slopeTo(1:end-1);
Brute_bigO = log2(mean(Brute_ratio))
display(['The order of grown of Brute is: N^', num2str(Brute_bigO)]);

Fast_exp = [114280,524166,2361484,10493474];
Fast_ratio = Fast_exp(2:end) ./ Fast_exp(1:end-1);
Fast_bigO = log2(mean(Fast_ratio))
display(['The order of grown of Fast is: N^', num2str(Fast_bigO)]);

Fast_exp = [2181156, 9587918, 41696136];
Fast_ratio = Fast_exp(2:end) ./ Fast_exp(1:end-1);
Fast_bigO = log2(mean(Fast_ratio))
display(['The order of grown of Fast is: N^', num2str(Fast_bigO)]);

Fast_exp = [2045022, 8846746, 37383758];
Fast_ratio = Fast_exp(2:end) ./ Fast_exp(1:end-1);
Fast_bigO = log2(mean(Fast_ratio))
display(['The order of grown of Fast is: N^', num2str(Fast_bigO)]);
