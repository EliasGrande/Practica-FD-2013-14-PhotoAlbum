#!/bin/sh

script_dir=$(dirname "$0")

echo
echo "--> Going to script folder..."
echo "--> [exec] cd \"$script_dir\""
cd "$script_dir"

echo
echo "--> Going to workspace folder..."
echo "--> [exec] cd ../../../.."
cd ../../../..

echo
echo "--> Getting workspace folder name..."
workspace_dirname=$(pwd | sed 's/.*\///')
workspace_tar="./$workspace_dirname.backup.tar"

echo
echo "--> Going to workspace parent folder..."
echo "--> [exec] cd .."
cd ..

echo
echo "--> Removing previous backup..."
echo "--> [exec] rm -f \"./$workspace_dirname.backup.tar\""
rm -f "./$workspace_dirname.backup.tar"

echo
echo "--> Building new backup..."
echo "--> [exec] tar -cvf \"$workspace_tar\" \"$workspace_dirname\""
tar -cvf "$workspace_tar" "$workspace_dirname"

echo
echo "--> Done."
echo
