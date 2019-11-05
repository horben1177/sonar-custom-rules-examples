class A {

  int foo() {}
  int foo(int a) {} // Noncompliant {{Avoid methods returning same type as their argument!}}
  int foo(int a, int b) {}

  Object foo(Object a){} // Noncompliant {{Avoid methods returning same type as their argument!}}
  String foo(String a){} // Noncompliant {{Avoid methods returning same type as their argument!}}
  String foo(Object a){}
}