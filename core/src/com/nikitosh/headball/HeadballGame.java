package com.nikitosh.headball;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HeadballGame extends Game {
	@Override
	public void create () {
		setScreen(new SplashScreen(this));
	}
}/*

package com.nikitosh.headball;

		import com.badlogic.gdx.ApplicationAdapter;
		import com.badlogic.gdx.Gdx;
		import com.badlogic.gdx.graphics.GL20;
		import com.badlogic.gdx.graphics.OrthographicCamera;
		import com.badlogic.gdx.graphics.Texture;
		import com.badlogic.gdx.graphics.g2d.SpriteBatch;
		import com.badlogic.gdx.math.Vector2;
		import com.badlogic.gdx.physics.box2d.Body;
		import com.badlogic.gdx.physics.box2d.BodyDef;
		import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
		import com.badlogic.gdx.physics.box2d.CircleShape;
		import com.badlogic.gdx.physics.box2d.FixtureDef;
		import com.badlogic.gdx.physics.box2d.PolygonShape;
		import com.badlogic.gdx.physics.box2d.Shape;
		import com.badlogic.gdx.physics.box2d.World;
		import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
		import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

public class HeadballGame extends ApplicationAdapter {
	World world;
	OrthographicCamera camera;
	Box2DDebugRenderer renderer;

	@Override
	public void create () {
		camera = new OrthographicCamera(100, 100);
		camera.position.set(0,0,0);

		world = new World(Vector2.Zero, true);

		renderer = new Box2DDebugRenderer();

		Body visibleCircle = createCircle(5, 0, 0);
		visibleCircle.setType(BodyDef.BodyType.DynamicBody);

		Body anchorPoint = createBox(2, 2, 0, 0);
		anchorPoint.setType(BodyDef.BodyType.DynamicBody);

		Body rotator = createBox(4,4,0,0);
		rotator.setType(BodyDef.BodyType.DynamicBody);

		Body box = createBox(4,4,8,0);
		box.setType(BodyDef.BodyType.DynamicBody);


		RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
		revoluteJointDef.initialize(anchorPoint, rotator, anchorPoint.getWorldCenter());
		revoluteJointDef.enableMotor = true;
		revoluteJointDef.motorSpeed = 20;
		revoluteJointDef.maxMotorTorque = 50;

		WeldJointDef weldJointDef = new WeldJointDef();
		weldJointDef.initialize(rotator, box, rotator.getWorldCenter());

		world.createJoint(revoluteJointDef);
		world.createJoint(weldJointDef);

	}

	private Body createBox(float w, float h, float x, float y) {
		BodyDef nodeBodyDefinition = new BodyDef();
		nodeBodyDefinition.type = BodyDef.BodyType.DynamicBody;
		nodeBodyDefinition.position.set(10, 10);

		PolygonShape shape = new PolygonShape();
		float density = 1.0f;
		shape.setAsBox(w / 2.0f, h / 2.0f);

		Body body = world.createBody(nodeBodyDefinition);
		body.setUserData(this);
		body.setTransform(x, y, 0);
		final FixtureDef nodeFixtureDefinition = createFixtureDefinition(shape, density);

		body.createFixture(nodeFixtureDefinition);
		shape.dispose();

		return body;
	}

	private Body createCircle(float r, float x, float y) {
		BodyDef nodeBodyDefinition = new BodyDef();
		nodeBodyDefinition.type = BodyDef.BodyType.DynamicBody;
		nodeBodyDefinition.position.set(10, 10);

		CircleShape shape = new CircleShape();
		float density = 1.0f;
		shape.setRadius(r);

		Body body = world.createBody(nodeBodyDefinition);
		body.setUserData(this);
		body.setTransform(x, y, 0);
		final FixtureDef nodeFixtureDefinition = createFixtureDefinition(shape, density);

		body.createFixture(nodeFixtureDefinition);
		shape.dispose();

		body.setLinearVelocity(1f, 0);
		return body;
	}

	private static FixtureDef createFixtureDefinition(final Shape shape, final float density) {
		final FixtureDef nodeFixtureDefinition = new FixtureDef();
		nodeFixtureDefinition.shape = shape;
		nodeFixtureDefinition.friction = 1;
		nodeFixtureDefinition.density = density;
		nodeFixtureDefinition.restitution = 0.1f;
		return nodeFixtureDefinition;
	}



	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.step(Gdx.graphics.getDeltaTime(), 4, 4);
		camera.update();

		renderer.render(world, camera.combined);
	}
}*/